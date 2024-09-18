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
    var partnerGarbage: MutableMap<Int, Tile> = mutableMapOf()
    var lastCoordinatingCorporation: Corporation? = null

    /**
     * Cooperation between ships
     *
     * Takes a list of all other ships in the simulation, checks which ships are on the same tile as the current ship,
     * then gets the field of view of the owner corporation of those ships and adds all garbage tiles to the
     * partnerGarbage map.
     *
     * @param otherShips List of all ships in the simulation
     */
    fun cooperate(otherShips: List<Ship>) {
        val myCoordinatingShips: List<Ship> = filterCoordinatingShips()

        myCoordinatingShips.forEach { coordinatingShip ->
            val otherShipsOnTile: List<Ship> = otherShips
                .filter {
                    coordinatingShip.position == it.position && it.owner != lastCoordinatingCorporation
                }

            val otherCorporations: List<Corporation> = otherShipsOnTile.map { it.owner }.distinct()
            val otherShipsToCooperate: List<Ship> =
                otherShips.filter { otherCorporations.contains(it.owner) }

            otherShipsToCooperate.forEach { otherShipToCooperate ->
                getInfoFromShip(otherShipToCooperate)
            }
            val lastCorporation: Corporation? = otherCorporations.maxByOrNull { it.id }
            lastCoordinatingCorporation = lastCorporation
        }
    }

    private fun getInfoFromShip(otherShip: Ship) {
        val telescopes: List<ScoutingShip> = otherShip.capabilities
            .filterIsInstance<ScoutingShip>()
        telescopes.forEach { telescope ->
            val tilesWithGarbage: List<Tile> =
                telescope.getTilesWithGarbageInFoV(Sea, otherShip.position)
            // partnerGarbage is a map of garbage id to tile
            tilesWithGarbage.forEach { tile ->
                tile.garbage
                    .asSequence()
                    .filter { acceptedGarbageType.contains(it.type) }
                    .forEach { garbage -> partnerGarbage[garbage.id] = tile }
            }
        }
    }

    /**
     * Main function to run the simulation
     *
     * Moves ships, collects garbage, cooperates with other ships, and refuels and unloads ships
     *
     * @param otherShips List of all ships in the simulation other than the current corporation's ships
     */
    fun run(otherShips: List<Ship>) {
        moveShips()
        collectGarbage()
        cooperate(otherShips)
        refuelAndUnloadShips()
    }

    /**
     * Gets all active tasks
     *
     * Filters the tasks list to get only the tasks that are currently active
     *
     * @return List of active tasks
     */
    fun getActiveTasks(): List<Task> {
        return tasks.filter { it.checkCondition() }
    }

    private fun tryMove(ship: Ship): Boolean {
        val capability = ship.capabilities.first()
        if (capability is ScoutingShip) {
            // 1. Update our knowledge about the garbage in the sea
            capability.getTilesWithGarbageInFoV(Sea, ship.position).forEach { tile ->
                tile.garbage
                    .asSequence()
                    .filter { acceptedGarbageType.contains(it.type) }
                    .forEach { garbage -> partnerGarbage[garbage.id] = tile }
            }
            // 2. Navigate to the closest garbage patch.
            val paths = Dijkstra(ship.position).allPaths()
            val sorted = paths.toList().sortedBy { it.second.size }
            val closestGarbagePatch = sorted
                .map { it.first }
                .intersect(partnerGarbage.values.toSet()).firstOrNull { tile ->
                    tile.garbage
                        .asSequence()
                        .filter { acceptedGarbageType.contains(it.type) }
                        .any { garbage -> !trackedGarbage.contains(garbage) }
                }
            if (closestGarbagePatch != null) {
                val path = paths[closestGarbagePatch] ?: return false
                if (ship.isFuelSufficient(path.size)) {
                    ship.move(path)
                } else {
                    val closestHarborPath = findClosestHarbor(ship.position, ownedHarbors)
                    ship.moveUninterrupted(closestHarborPath)
                }
                return true
            }
            return false
        } else if (capability is CollectingShip) {
            // TODO
            return false
        } else if (capability is CoordinatingShip) {
            // TODO
            return false
        }
        error("Unknown ship capability")
    }

    /** Documentation for getShipsOnHarbor Function && removed sea:Sea from moveShips Signature
     *  todo Handle restrictions **/
    private fun moveShips() {
        val availableShips: MutableSet<Ship> = ownedShips.toMutableSet()
        // 0. For each ship that has an assigned destination, tick the
        // ship and remove the ship from the available ships
        availableShips.forEach { if (it.hasTaskAssigned) it.tickTask() }
        availableShips.removeAll { it.hasTaskAssigned }
        // 1. Process tasks. For each active task, assign the ship from the task to
        // go to the target tile.
        val activeTasks: List<Task> = getActiveTasks()
        for (task in activeTasks) {
            val ship: Ship = task.taskShip
            val targetTile: Tile = task.getGoal()
            Dijkstra(targetTile).allPaths()[ship.position]?.let { path ->
                if (ship.isFuelSufficient(path.size)) {
                    ship.move(path)
                    availableShips.remove(ship)
                } else {
                    val closestHarborPath: List<Tile> = findClosestHarbor(ship.position, ownedHarbors)
                    ship.moveUninterrupted(closestHarborPath)
                    availableShips.remove(ship)
                }
            }
        }
        // 2. Iterate over available ships in increasing ID order
        val usedShips: MutableList<Int> = mutableListOf()
        for (ship in availableShips.sortedBy { it.id }) {
            val status = tryMove(ship)
            if (status) {
                usedShips.add(ship.id)
            }
        }
        availableShips.removeAll { usedShips.contains(it.id) }
        // 3. Unused ships are jobless. Something might happen to them here.
    }

    /**
     * Collects garbage from the current tile
     *
     * Filters the ships to get only the ships that have the CollectingShip capability, then collects garbage from the
     * current tile of each ship
     *
     */
    private fun collectGarbage() {
        val collectingShips: List<Ship> = filterCollectingShip()
        for (ship in collectingShips) {
            for (collectingCapability in ship.capabilities) {
                (collectingCapability as CollectingShip).collectGarbageFromCurrentTile(ship.position)
            }
        }
    }

    /**
     * Refuels and unloads ships
     *
     * Gets all the ships on the harbor, then unloads the ships that have the CollectingShip capability
     * and refuels all the ships
     *
     */
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

    /**
     * Gets all the ships on the harbor
     *
     * Filters the owned ships to get only the ships that are on a harbor tile
     *
     * @return List of ships on the harbor
     */
    private fun getShipsOnHarbor(): List<Ship> {
        val seaInstance: Sea = Sea
        val harborTiles: List<Tile> = seaInstance.tiles.filter { (it as Shore).harbor }
        return ownedShips.filter { harborTiles.contains(it.position) }
    }

    /**
     * Find the closest ship to a tile
     *
     * Takes destination tile and list of ships as input, then uses Dijkstra's algorithm to find the
     * shortest path to the closest ship
     *
     * make it private later
     * @param tile Destination tile
     * @param ships List of ships
     * @return List of tiles representing the shortest path to the closest ship
     */
    fun findClosestShip(tile: Tile, ships: List<Ship>): List<Tile> {
        val dijkstra: Dijkstra = Dijkstra(tile)
        val shortestPaths: Map<Tile, List<Tile>> = dijkstra.allPaths()

        var shortestPathLen: Int = Int.MAX_VALUE
        var shortestPath: List<Tile> = emptyList()

        for (ship in ships) {
            val path: List<Tile>? = shortestPaths[ship.position]
            if (path != null && path.size < shortestPathLen) {
                shortestPathLen = path.size
                shortestPath = path
            }
        }

        return shortestPath
    }

    /**
     * Find the closest harbor to a tile
     *
     * Takes destination tile and list of harbors as input, then uses Dijkstra's algorithm to find the
     * shortest path to the closest harbor
     *
     * @param tile Destination tile
     * @param harbors List of harbors
     * @return List of tiles representing the shortest path to the closest harbor
     */
    private fun findClosestHarbor(tile: Tile, harbors: List<Shore>): List<Tile> {
        val dijkstra: Dijkstra = Dijkstra(tile)
        val shortestPaths: Map<Tile, List<Tile>> = dijkstra.allPaths()

        var shortestPathLen: Int = Int.MAX_VALUE
        var shortestPath: List<Tile> = emptyList()

        for (harbor in harbors) {
            val path: List<Tile>? = shortestPaths[harbor]
            if (path != null && path.size < shortestPathLen) {
                shortestPathLen = path.size
                shortestPath = path
            }
        }

        return shortestPath
    }

    /**
     * Filter collecting ships
     *
     * Filters the owned ships to get only the ships that have the CollectingShip capability
     *
     * @return List of collecting ships
     */
    private fun filterCollectingShip(): List<Ship> {
        return ownedShips.filter { ownedShip -> ownedShip.capabilities.any { it is CollectingShip } }
    }

    /**
     * Filter scouting ships
     *
     * Filters the owned ships to get only the ships that have the ScoutingShip capability
     * make it private later
     *
     * @return List of scouting ships
     */
    fun filterScoutingShips(): List<Ship> {
        return ownedShips.filter { ownedShip -> ownedShip.capabilities.any { it is ScoutingShip } }
    }

    /**
     * Filter coordinating ships
     *
     * Filters the owned ships to get only the ships that have the CoordinatingShip capability
     *
     * @return List of coordinating ships
     */
    private fun filterCoordinatingShips(): List<Ship> {
        return ownedShips.filter { ownedShip -> ownedShip.capabilities.any { it is CoordinatingShip } }
    }
}
