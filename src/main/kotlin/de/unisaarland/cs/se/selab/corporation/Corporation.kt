package de.unisaarland.cs.se.selab.corporation

import de.unisaarland.cs.se.selab.logger.LoggerCorporationAction
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.MovementTuple
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.ships.ShipWithTracker
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
    val tasks: MutableList<Task>
) {
    /**
     * Some constants.
     */
    companion object {
        const val INFTY = 1000000
    }

    val trackedGarbage: MutableList<Garbage> = mutableListOf()
    val partnerGarbage: MutableMap<Int, Tile> = mutableMapOf()
    var lastCoordinatingCorporation: Corporation? = null
    val logger: LoggerCorporationAction = LoggerCorporationAction
    lateinit var sea: Sea
    private var activeTasks: List<Task> = emptyList()

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
        val myCoordinatingShips: List<Ship> = Helper().filterCoordinatingShips(this).sortedBy { it.id }

        myCoordinatingShips.forEach { coordinatingShip ->
            val otherShipsOnTile: List<Ship> = otherShips
                .filter {
                    coordinatingShip.position == it.position && it.owner != lastCoordinatingCorporation
                }.sortedBy { it.id }

            val otherCorporations: List<Corporation> = otherShipsOnTile.map { it.owner }.distinct().sortedBy { it.id }

            otherCorporations.forEach {
                    corporation ->
                val toCooperateWith = corporation.ownedShips.filter { otherShipsOnTile.contains(it) }
                    .sortedBy { it.id }.first()
                getInfoFromShip(toCooperateWith, coordinatingShip)
                lastCoordinatingCorporation = otherCorporations.maxByOrNull { it.id }
                otherCorporations.minus(corporation)
            }
        }
    }

    private fun getInfoFromShip(otherShip: Ship, coordinatingShip: Ship) {
        val telescopes: List<ScoutingShip> = otherShip.capabilities
            .filterIsInstance<ScoutingShip>()
        telescopes.forEach { telescope ->
            val tilesWithGarbage: List<Tile> =
                telescope.getTilesWithGarbageInFoV(sea, otherShip.position)
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

    private fun tryAttachTrackers() {
        val shipsWithTrackers: List<Ship> = ownedShips.filter { it.capabilities.any { x -> x is ShipWithTracker } }
        shipsWithTrackers.forEach { ship ->
            // Get all garbage on the current tile
            val garbageOnTile: List<Garbage> = ship.position.garbage
            garbageOnTile.forEach { garbage ->
                if (!trackedGarbage.contains(garbage)) {
                    trackedGarbage.add(garbage)
                    garbage.trackedBy = garbage.trackedBy.plus(this)
                    LoggerCorporationAction.logAttachTracker(id, ship.id, garbage.id)
                }
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
    fun run(tick: Int, sea: Sea, otherShips: List<Ship>) {
        this.sea = sea
        getActiveTasks(tick)
        logger.logCorporationStartMoveShips(id)
        moveShips(otherShips)
        tryAttachTrackers()
        logger.logCorporationStartCollectGarbage(id)
        collectGarbage(otherShips)
        logger.logCorporationCooperationStart(id)
        cooperate(otherShips)
        logger.logCorporationRefueling(id)
        refuelAndUnloadShips()
        logger.logCorporationFinishedActions(id)
    }

    private fun getActiveTasks(tick: Int): List<Task> {
        activeTasks = tasks.filter { tick == it.tick + 1 }
        return activeTasks
    }

    private fun findUncollectedGarbage(tile: Tile, cap: CollectingShip, target: MutableMap<Int, Int>): Garbage? {
        return tile.garbage
            .asSequence()
            .filter { cap.garbageTypes().contains(it.type) }
            .filter {
                if (target.contains(it.id)) {
                    return@filter (target[it.id] ?: error("there should be a target")) < it.amount
                } else {
                    return@filter true
                }
            }
            .sortedBy { it.id }
            .firstOrNull()
    }

    private fun getPosOfGarbage(garbage: Garbage): Tile {
        sea.tiles.forEach { tile ->
            if (tile.garbage.contains(garbage)) {
                return tile
            }
        }
        error("Garbage not found")
    }

    private fun updateScoutFOV(capability: ScoutingShip, ship: Ship) {
        capability.getTilesWithGarbageInFoV(sea, ship.position).forEach { tile ->
            tile.garbage
                .asSequence()
                .filter { acceptedGarbageType.contains(it.type) }
                .forEach { garbage -> partnerGarbage[garbage.id] = tile }
            tile.garbage.forEach {
                if (partnerGarbage[it.id] != tile) {
                    partnerGarbage.remove(it.id) // Remove if the garbage is on a different tile...
                }
            }
        }
    }

    private fun moveScoutingShip(ship: Ship, scoutTarget: MutableSet<Int>): Boolean {
        var result: Boolean = false
        // 2. Navigate to the closest garbage patch.
        val paths = Dijkstra(ship.position).allPaths()
        val sorted = paths.toList().sortedWith(
            compareBy(
                { it.second.size },
                { it.first.garbage.minOfOrNull { g -> g.id } }
            )
        )
        val closestGarbagePatch = sorted
            .map { it.first }
            .intersect(partnerGarbage.values.toSet().union(trackedGarbage.map { getPosOfGarbage(it) }).toSet())
            .filter { !scoutTarget.contains(it.id) }
            .firstOrNull { tile ->
                tile.garbage
                    .asSequence()
                    .filter { acceptedGarbageType.contains(it.type) }
                    .any()
            }
        if (closestGarbagePatch != null && closestGarbagePatch != ship.position) {
            val path = paths[closestGarbagePatch] ?: return false
            if (ship.isFuelSufficient(path.size, ownedHarbors, closestGarbagePatch)) {
                ship.move(path)
                scoutTarget.add(closestGarbagePatch.id)
            } else {
                val closestHarborPath = Helper().findClosestHarbor(ship.position, ownedHarbors)
                ship.moveUninterrupted(closestHarborPath, false, true)
            }
            result = true
        } else if (closestGarbagePatch != ship.position) {
            // Explore: Navigate to the furthest tile
            val dest = paths.toList().sortedWith(compareBy({ INFTY - it.second.size }, { it.first.id }))
                .first { it.second.size <= ship.speed() + 1 }.first
            val path = paths[dest] ?: return false
            if (ship.isFuelSufficient(path.size, ownedHarbors, dest)) {
                ship.move(path)
            } else {
                val closestHarborPath = Helper().findClosestHarbor(ship.position, ownedHarbors)
                ship.moveUninterrupted(closestHarborPath, false, true)
            }
            result = true
        }
        return result
    }

    // ships move in the wrong order if they taskAssigned = true
    private fun moveCollectingShip(ship: Ship, cap: CollectingShip, collectorTarget: MutableMap<Int, Int>): Boolean {
        val result: Boolean
        // May not handle the fact that plastic needs collected all at once
        // 1. Determine if we're on a garbage tile that we can collect
        val garbage = ship.position.garbage
            .asSequence()
            .filter { acceptedGarbageType.contains(it.type) && cap.garbageTypes().contains(it.type) }
            .sortedBy { it.id }.toList()
        if (garbage.isNotEmpty()) {
            // Don't move. Add info about the garbage on this tile to corp knowledge.
            ship.position.garbage.forEach {
                partnerGarbage[it.id] = ship.position
            }
            if (ship.isCapacitySufficient(garbage)) {
                result = true
                ship.currentVelocity = 0
            } else {
                val closestHarborPath = Helper().findClosestHarbor(ship.position, ownedHarbors)
                ship.moveUninterrupted(closestHarborPath, false, true)
                result = true
            }
        } else {
            // Navigate to the closest garbage patch that it can collect.
            val paths = Dijkstra(ship.position).allPaths()
            val sorted = paths.toList().sortedWith(compareBy({ it.second.size }, { it.first.id }))
            // if two paths have equal length, then the garbage with lowest id
            val attainableGarbage = sorted
                .map { it.first }
                .intersect(partnerGarbage.values.toSet().union(trackedGarbage.map { getPosOfGarbage(it) }).toSet())
                .filter { tile ->
                    findUncollectedGarbage(tile, cap, collectorTarget) != null
                }.sortedWith(compareBy({ paths[it]?.size }, { it.garbage.first().id }))

            // attainableGarbage is a set of tiles that have garbage that the ship can collect
            // and requires extra ships to be dispatched. Just take the first one:
            val closestGarbagePatch = attainableGarbage.firstOrNull()
            if (closestGarbagePatch != null) {
                val path = paths[closestGarbagePatch] ?: return false
                if (ship.isFuelSufficient(path.size, ownedHarbors, closestGarbagePatch) &&
                    ship.isCapacitySufficient(closestGarbagePatch.garbage)
                ) {
                    ship.move(path)
                } else {
                    val closestHarborPath = Helper().findClosestHarbor(ship.position, ownedHarbors)
                    ship.moveUninterrupted(closestHarborPath, false, true)
                }
                result = true
            } else {
                result = false
            }
        }
        return result
    }

    private fun tryMove(
        ship: Ship,
        scoutTarget: MutableSet<Int>,
        collectorTarget: MutableMap<Int, Int>,
        otherShips: List<Ship>,
        capabilityIndex: Int = 0
    ): Boolean {
        val result: Boolean
        val capability = ship.capabilities[capabilityIndex]
        result = if (capability is ScoutingShip) {
            moveScoutingShip(ship, scoutTarget)
        } else if (capability is CollectingShip) {
            moveCollectingShip(ship, capability, collectorTarget)
        } else if (capability is CoordinatingShip) {
            handleMoveCoordinating(ship, capability, otherShips)
        } else {
            error("Unknown ship capability")
        }
        if (!result && capabilityIndex + 1 < ship.capabilities.size) {
            return tryMove(ship, scoutTarget, collectorTarget, otherShips, capabilityIndex + 1)
        }
        return result
    }

    private fun tickTasksInMoveShips(availableShips: MutableSet<Ship>) {
        availableShips.removeIf {
            if (it.hasTaskAssigned || it.isInWayToRefuelOrUnload) {
                it.tickTask(it.hasTaskAssigned, it.isInWayToRefuelOrUnload)
                return@removeIf true
            }
            return@removeIf false
        }
    }

    private fun moveShipsOutOfRestriction(availableShips: MutableSet<Ship>) {
        // BUG POTENTIAL
        availableShips.filter {
            it.position.restrictions > 0
        }.forEach {
            val path = Dijkstra(it.position).allPaths()
            val destination = path.toList()
                .sortedWith(compareBy({ x -> x.second.size }, { x -> x.first.id }))
                .map { x -> x.first }.firstOrNull { x -> x.restrictions == 0 }
            if (destination != null) {
                it.move(path[destination] ?: error("There should be a path..."))
                availableShips.remove(it)
            }
        }
    }

    /** Documentation for getShipsOnHarbor Function && removed sea:Sea from moveShips Signature **/
    private fun moveShips(otherShips: List<Ship>) {
        ownedShips.forEach { it.movedThisTick = MovementTuple(false, -1, -1, -1) }
        val availableShips: MutableSet<Ship> = ownedShips.toMutableSet()
        // -1. Move ships that are inside a restriction out of a restriction
        moveShipsOutOfRestriction(availableShips)
        // 1. Process tasks. For each active task, assign the ship from the task to
        // go to the target tile.
        // val activeTasks: List<Task> = getActiveTasks()
        activeTasks.forEach { task ->
            val ship: Ship = task.taskShip
            /**
             * This is my fix so far for this, hasTaskAssigned is false if the ship is doing a task, hence can be
             * overwritten, if it's going to refuel or unload this will be set to false
             */
            if (!ship.isInWayToRefuelOrUnload) {
                makeMovement(task, ship, availableShips)
            } else {
                // Task failed, ship is going to refuel/unload
                tasks.remove(task)
            }
        }
        // 0. For each ship that has an assigned destination, tick the
        // ship and remove the ship from the available ships
        tickTasksInMoveShips(availableShips)
        val usedShips = helpermoveShips(availableShips, otherShips)
        availableShips.removeAll { usedShips.contains(it.id) }
        ownedShips.filter { it.movedThisTick.moved }.sortedBy { it.id }.forEach {
            LoggerCorporationAction.logShipMovement(
                it.movedThisTick.shipId,
                it.movedThisTick.velocity,
                it.movedThisTick.destinationId
            )
        }
        // 3. Unused ships are jobless. Something might happen to them here.
    }

    private fun makeMovement(task: Task, ship: Ship, availableShips: MutableSet<Ship>) {
        val targetTile: Tile = task.getGoal()
        Dijkstra(targetTile).allPaths()[ship.position]?.let { path ->
            if (ship.isFuelSufficient(path.size, this.ownedHarbors, targetTile)) {
                ship.moveUninterrupted(path.reversed(), true, false)
                availableShips.remove(ship)
            } else {
                // WE SHOULD ADD A REFUELING HERE
                val closestHarborPath = Helper().findClosestHarbor(ship.position, ownedHarbors)
                ship.moveUninterrupted(closestHarborPath, false, true)
                // Task failed, not enough fuel.
                tasks.remove(task)
            }
        }
    }
    private fun helpermoveShips(availableShips: MutableSet<Ship>, otherShips: List<Ship>): MutableList<Int> {
        // 2. Iterate over available ships in increasing ID order
        val usedShips: MutableList<Int> = mutableListOf()
        val scoutTarget: MutableSet<Int> = mutableSetOf()
        val collectorTarget: MutableMap<Int, Int> = mutableMapOf()
        for (ship in availableShips.sortedBy { it.id }) {
            ship.capabilities.forEach {
                if (it is ScoutingShip) {
                    updateScoutFOV(it, ship)
                }
            }
            if (tryMove(ship, scoutTarget, collectorTarget, otherShips)) {
                usedShips.add(ship.id)
            }
        }
        return usedShips
    }
    private fun handleMoveCoordinating(ship: Ship, capability: CoordinatingShip, otherShips: List<Ship>): Boolean {
        val result: Boolean
        // 1. Get information about which ships are in field of view
        val tilesInFov = capability.getTilesInFoV(sea, ship.position)
        val shipFov = otherShips.filter { tilesInFov.contains(it.position) && it.owner != lastCoordinatingCorporation }

        // 2. Check for ships on the current tile of the coordinating ship
        val onPos = shipFov.firstOrNull { it.position == ship.position && it.owner != lastCoordinatingCorporation }
        result = if (onPos != null) {
            true
        } else {
            helperMoveCoordinating(ship, otherShips)
        }
        return result
    }
    private fun helperMoveCoordinating(ship: Ship, otherShips: List<Ship>): Boolean {
        // 3. Navigate to the closest ship
        val closestShipPath = Helper().findClosestShip(ship.position, otherShips)
        if (closestShipPath.isNotEmpty()) {
            if (ship.isFuelSufficient(closestShipPath.size, ownedHarbors, closestShipPath.last())) {
                ship.move(closestShipPath)
            } else {
                val closestHarborPath = Helper().findClosestHarbor(ship.position, ownedHarbors)
                ship.moveUninterrupted(closestHarborPath, false, true)
            }
        } else {
            // Explore: Navigate to the furthest tile
            val paths = Dijkstra(ship.position).allPaths()
            val dest = paths.toList().sortedWith(compareBy({ INFTY - it.second.size }, { it.first.id }))
                .first { it.second.size <= ship.speed() + 1 }.first
            val path = paths[dest] ?: return false
            if (ship.isFuelSufficient(path.size, ownedHarbors, dest)) {
                ship.move(path)
            } else {
                val closestHarborPath = Helper().findClosestHarbor(ship.position, ownedHarbors)
                ship.moveUninterrupted(closestHarborPath, false, true)
            }
        }
        return true
    }

    /**
     * Collects garbage from the current tile
     *
     * Filters the ships to get only the ships that have the CollectingShip capability, then collects garbage from the
     * current tile of each ship
     */
    private fun collectGarbage(otherShips: List<Ship>) {
        val collectingShips: List<Ship> = Helper().filterCollectingCapabilities(this).sortedBy { it.id }
        for (ship in collectingShips) {
            val capability = ship.capabilities.filterIsInstance<CollectingShip>()
            for (cap in capability) {
                cap.collectGarbageFromCurrentTile(ship, otherShips, ownedShips)
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
        /**
         * NOT SURE ABOUT THIS BUT: WHEN A SHIP GOES TO HARBOR FOR REFUEL/UNLOAD, hastaskassigned is set to true,
         * but it should be set again to false after reload/refuel in order to be usable next tick ????
         */
        val collectingShips: List<Ship> = Helper().filterCollectingCapabilities(this).sortedBy { it.id }
        val shipsOnHarbor: List<Ship> = Helper().getShipsOnHarbor(this)
        if (shipsOnHarbor.isNotEmpty()) {
            for (ship in shipsOnHarbor) {
                var capability: CollectingShip? = null
                if (collectingShips.contains(ship)) {
                    capability = ship.capabilities.filterIsInstance<CollectingShip>().first()
                }
                if (ship.refueling) {
                    ship.refuel()
                    // ship.currentVelocity = 0
                } else if (capability != null && capability.unloading) {
                    ship.isInWayToRefuelOrUnload = !capability.unload(ship)
                }
            }
        }
    }

    /**
     * private fun getGarbageInfo(): List<Garbage> {
     *         // WE NEED A METHOD THAT RETURNS ALL THE GARBAGE THE CORPORATION KNOWS ABOUT HERE
     *         return mutableListOf<Garbage>()
     *     }
     */
}
