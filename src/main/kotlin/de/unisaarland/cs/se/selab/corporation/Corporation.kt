package de.unisaarland.cs.se.selab.corporation
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D

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
    var partnerGarbage: MutableMap<Int, Vec2D> = mutableMapOf()
    var lastCoordinatingCorporation: Corporation? = null

    /** Documentation for cooperate Function **/
    public fun cooperate(ships: List<Ship>): Corporation {
        val myCoordinatingShips: List<Ship> = filterCoordinatingShips()

        myCoordinatingShips.forEach { coordinatingShip ->
            val otherShip: Ship? = ships.find { coordinatingShip.getPos() == it.getPos() }
            if (otherShip != null) {
                for (ship in otherShip.getOwner().ownedShips) {
                    val coordinatingCapability = ship.capabilities.find { it is CoordinatingShip }
                    if (coordinatingCapability != null) {
                        TODO(TODO)
                    }
                }
            }
        }

        TODO(TODO)
    }

    /** Documentation for run Function **/
    public fun run(otherShips: List<Ship>) {
        moveShips()
        collectGarbage()
        cooperate(otherShips)
        refuelAndUnloadShips()
    }

    /** Documentation for getActiveTasks Function **/
    public fun getActiveTasks(): List<Task> {
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
    private fun findClosestShip(sourceMap: Map<Tile, List<Tile>>, ships: List<Ship>) {
        TODO(TODO)
    }

    /** Documentation for findClosestHarbor Function **/
    private fun findClosestHarbor(sourceMap: Map<Tile, List<Tile>>, harbors: List<Shore>) {
        TODO(TODO)
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
