package de.unisaarland.cs.se.selab.corporation

import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.Dijkstra
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile

const val TODO: String = "Yet to implement"

/** Documentation for Corporation Class **/
class Corporation(
    val id: Int,
    val name: String,
    val ownedShips: MutableList<Ship>,
    val ownedHarbors: List<Shore>,
    val acceptedGarbageType: List<GarbageType>,
    val tasks: List<Task>
) {
    var trackedGarbage: MutableList<Garbage> = mutableListOf()
    var partnerGarbage: MutableMap<Int, MutableList<Tile>> = mutableMapOf()
    var lastCoordinatingCorporation: Corporation? = null

    /** Documentation for cooperate Function **/
    fun cooperate(otherShips: List<Ship>) {
        val myCoordinatingShips: List<Ship> = filterCoordinatingShips()

        myCoordinatingShips.forEach { coordinatingShip ->
            val otherShipsOnTile: List<Ship> = otherShips
                .filter { coordinatingShip.getPos() == it.getPos() && it.getOwner() != lastCoordinatingCorporation}
            val otherCorporations: List<Corporation> = otherShipsOnTile.map { it.getOwner() }.distinct()

            val otherShipsToCooperate: List<Ship> = otherShips.filter { otherCorporations.contains(it.getOwner()) }

            otherShipsToCooperate.forEach { otherShipToCooperate ->
                val telescopes: List<ScoutingShip> = otherShipToCooperate.capabilities
                    .filterIsInstance<ScoutingShip>()
                telescopes.forEach {
                    val tilesWithGarbage: List<Tile> = it.getTilesWithGarbageInFoV(Sea, otherShipToCooperate.getPos()!!)
                    partnerGarbage.getOrPut(otherShipToCooperate.getOwner().id) { mutableListOf() } += tilesWithGarbage
                }
            }
        }

    }

    /** Documentation for run Function **/
    fun run(otherShips: List<Ship>) {
        moveShips()
        collectGarbage()
        cooperate(otherShips)
        refuelAndUnloadShips()
    }

    /** Documentation for getActiveTasks Function **/
    fun getActiveTasks(): List<Task> {
        return tasks.filter { it.checkCondition() }
    }

    /** Documentation for getShipsOnHarbor Function **/
    private fun moveShips() {
        TODO(TODO)
    }

    /** Documentation for collectGarbage Function **/
    private fun collectGarbage() {
        val collectingShips: List<Ship> = filterCollectingShip()
        for (ship in collectingShips) {
            for (collectingCapability in ship.capabilities) {
                (collectingCapability as CollectingShip).collectGarbageFromCurrentTile(ship.getPos())
            }
        }
    }

    /** Documentation for refuelAndUnloadShips Function **/
    private fun refuelAndUnloadShips() {
        val shipsOnHarbor: List<Ship> = getShipsOnHarbor()
        if (shipsOnHarbor.isNotEmpty()) {
            for (ship in shipsOnHarbor) {
                val collectingCapability = ship.capabilities.find { it is CollectingShip }
                if (collectingCapability != null) {
                    (collectingCapability as CollectingShip).unload()
                }
                ship.refuel()
            }
        }
    }

    /** Documentation for getShipsOnHarbor Function **/
    private fun getShipsOnHarbor(): List<Ship> {
        val seaInstance: Sea = Sea
        val harborTiles: List<Tile> = seaInstance.tiles.filter { (it as Shore).harbor }
        return ownedShips.filter { harborTiles.contains(it.getPos()) }
    }

    /** Documentation for findClosestShip Function **/
    private fun findClosestShip(tile: Tile, ships: List<Ship>): List<Tile> {
        val dijkstra: Dijkstra = Dijkstra(tile)
        val shortestPaths: Map<Tile, List<Tile>> = dijkstra.allPaths()

        var shortestPathLen: Int = Int.MAX_VALUE
        var shortestPath: List<Tile> = listOf()

        for (ship in ships) {
            val path: List<Tile>? = shortestPaths[ship.getPos()]
            if (path != null && path.size < shortestPathLen) {
                shortestPathLen = path.size
                shortestPath = path
            }
        }

        return shortestPath
    }

    /** Documentation for findClosestHarbor Function **/
    private fun findClosestHarbor(tile: Tile, harbors: List<Shore>): List<Tile> {
        val dijkstra: Dijkstra = Dijkstra(tile)
        val shortestPaths: Map<Tile, List<Tile>> = dijkstra.allPaths()

        var shortestPathLen: Int = Int.MAX_VALUE
        var shortestPath: List<Tile> = listOf()

        for (harbor in harbors) {
            val path: List<Tile>? = shortestPaths[harbor]
            if (path != null && path.size < shortestPathLen) {
                shortestPathLen = path.size
                shortestPath = path
            }
        }

        return shortestPath
    }

    /** Documentation for filterCollectingShip Function **/
    private fun filterCollectingShip(): List<Ship> {
        return ownedShips.filter { ownedShip -> ownedShip.capabilities.any { it is CollectingShip } }
    }

    /** Documentation for filterScoutingShips Function **/
    private fun filterScoutingShips(): List<Ship> {
        return ownedShips.filter { ownedShip -> ownedShip.capabilities.any { it is ScoutingShip } }
    }

    /** Documentation for filterCoordinatingShips Function **/
    private fun filterCoordinatingShips(): List<Ship> {
        return ownedShips.filter { ownedShip -> ownedShip.capabilities.any { it is CoordinatingShip } }
    }
}
