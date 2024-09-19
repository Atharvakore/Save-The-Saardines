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
    val trackedGarbage: MutableList<Garbage> = mutableListOf()
    val partnerGarbage: MutableMap<Int, Tile> = mutableMapOf()
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
        val myCoordinatingShips: List<Ship> = Helper().filterCoordinatingShips(this)

        myCoordinatingShips.forEach { coordinatingShip ->
            val otherShipsOnTile: List<Ship> = otherShips
                .filter {
                    coordinatingShip.position == it.position && it.owner != lastCoordinatingCorporation
                }

            val otherCorporations: List<Corporation> = otherShipsOnTile.map { it.owner }.distinct()
            val otherShipsToCooperate: List<Ship> =
                otherShips.filter { otherCorporations.contains(it.owner) }

            otherShipsToCooperate.forEach { otherShipToCooperate ->
                getInfoFromShip(otherShipToCooperate, coordinatingShip)
            }
            val lastCorporation: Corporation? = otherCorporations.maxByOrNull { it.id }
            lastCoordinatingCorporation = lastCorporation
        }
    }

    private fun getInfoFromShip(otherShip: Ship, coordinatingShip: Ship) {
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
        LoggerCorporationAction.logCooperationBetweenCorporations(
            id,
            otherShip.owner.id,
            coordinatingShip.id,
            otherShip.id
        )
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

    private fun findUncollectedGarbage(tile: Tile, cap: CollectingShip, target: MutableMap<Int, Int>): Garbage? {
        return tile.garbage
            .asSequence()
            .filter { cap.garbageTypes().contains(it.type) }
            .filter {
                if (target.contains(it.id)) {
                    return@filter target[it.id]!! < it.amount
                } else {
                    return@filter true
                }
            }
            .sortedBy { it.id }
            .firstOrNull()
    }

    private fun moveScoutingShip(capability: ScoutingShip, ship: Ship, scoutTarget: MutableSet<Int>): Boolean {
        val result: Boolean
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
            .intersect(partnerGarbage.values.toSet())
            .filter { !scoutTarget.contains(it.id) }
            .firstOrNull { tile ->
                tile.garbage
                    .asSequence()
                    .filter { acceptedGarbageType.contains(it.type) }
                    .any()
            }
        if (closestGarbagePatch != null) {
            val path = paths[closestGarbagePatch] ?: return false
            if (ship.isFuelSufficient(path.size)) {
                ship.move(path)
                scoutTarget.add(closestGarbagePatch.id)
            } else {
                val closestHarborPath = Helper().findClosestHarbor(ship.position, ownedHarbors)
                ship.moveUninterrupted(closestHarborPath)
            }
            result = true
        } else {
            result = false
        }
        return result
    }

    private fun tryMove(
        ship: Ship,
        scoutTarget: MutableSet<Int>,
        collectorTarget: MutableMap<Int, Int>,
        otherShips: List<Ship>
    ): Boolean {
        var result: Boolean
        val capability = ship.capabilities.first()
        if (capability is ScoutingShip) {
            result = moveScoutingShip(capability, ship, scoutTarget)
        } else if (capability is CollectingShip) {
            //  may not handle the fact that plastic needs collected all at once
            // 1. Determine if we're on a garbage tile that we can collect
            val garbage = ship.position.garbage
                .asSequence()
                .filter { acceptedGarbageType.contains(it.type) && capability.garbageTypes().contains(it.type) }
                .sortedBy { it.id }
                .firstOrNull()
            if (garbage != null) {
                capability.collectGarbageFromCurrentTile(ship.position)
                result = true
            } else {
                // Navigate to the closest garbage patch that it can collect.
                val paths = Dijkstra(ship.position).allPaths()
                val sorted = paths.toList().sortedBy { it.second.size }
                val attainableGarbage = sorted
                    .map { it.first }
                    .intersect(partnerGarbage.values.toSet())
                    .filter { tile ->
                        findUncollectedGarbage(tile, capability, collectorTarget) != null
                    }
                // attainableGarbage is a set of tiles that have garbage that the ship can collect
                // and requires extra ships to be dispatched. Just take the first one:
                val closestGarbagePatch = attainableGarbage.firstOrNull()
                if (closestGarbagePatch != null) {
                    val path = paths[closestGarbagePatch] ?: return false
                    if (ship.isFuelSufficient(path.size)) {
                        ship.move(path)
                        val pile = findUncollectedGarbage(closestGarbagePatch, capability, collectorTarget)!!
                        // Dodgy logic?
                        val amount = collectorTarget.getOrDefault(pile.id, 0) +
                            min(capability.capacityForType(pile.type), pile.amount)
                        collectorTarget[pile.id] = min(amount, pile.amount)
                    } else {
                        val closestHarborPath = Helper().findClosestHarbor(ship.position, ownedHarbors)
                        ship.moveUninterrupted(closestHarborPath)
                    }
                    result = true
                } else {
                    result = false
                }
            }
        } else if (capability is CoordinatingShip) {
            // TODO
            result = false
        } else {
            error("Unknown ship capability")
        }
        return result
    }

    /** Documentation for getShipsOnHarbor Function && removed sea:Sea from moveShips Signature **/
    private fun moveShips(otherShips: List<Ship>) {
        val availableShips: MutableSet<Ship> = ownedShips.toMutableSet()
        // -1. Move ships that are inside a restriction out of a restriction
        availableShips.filter {
            it.position.restrictions > 0
        }.forEach {
            val path = Dijkstra(it.position).allPaths()
            val destination = path.keys.firstOrNull { it.restrictions == 0 }
            if (destination != null) {
                it.move(path[destination]!!)
                availableShips.remove(it)
            }
        }
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
        val collectingShips: List<Ship> = Helper().filterCollectingShip(this)
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
        val shipsOnHarbor: List<Ship> = Helper().getShipsOnHarbor(this)
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
}
