package de.unisaarland.cs.se.selab

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.events.Event
import de.unisaarland.cs.se.selab.logger.Logger
import de.unisaarland.cs.se.selab.logger.LoggerStatistics
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.TEN
import de.unisaarland.cs.se.selab.tiles.Tile
import java.util.PriorityQueue

/**
 * class that represents and handles the simulation
 */
class Simulation(
    private val corporations: List<Corporation>,
    private val allEvents: List<Event>,
    private val maxTick: Int,
    private val sea: Sea,
) {
    private var tick: Int = 0
    val activeTasks: MutableMap<Task, Boolean> = mutableMapOf()

    /**
     * starts the whole simulation and enters a loop that runs tick() until maxTick is reached
     */
    fun start() {
        Logger.logSimulationStarted()
        while (tick < maxTick) {
            Logger.logSimulationTick(tick)
            tick()
            tick++
        }
        Logger.logSimulationEnded()
        LoggerStatistics.garbageOnMap = sea.calculateGarbageOnMap()
        LoggerStatistics.logSimulationStatistics(corporations)
    }

    /**
     * each call to tick corresponds to one tick in the simulation
     * it handles all the simulation logic by calling the different methods in order
     */
    private fun tick() {
        runCorporations()
        driftGarbage()
        driftShips()
        processEvents()
        processTasks()
    }

    /**
     * iterates over all corporations and calls run() on them
     */
    private fun runCorporations() {
        val allShips = mutableListOf<Ship>()
        for (corporation in corporations) {
            allShips.addAll(corporation.ownedShips)
        }

        for (corporation in corporations.sortedBy { it.id }) {
            val otherShips = allShips.filter { it.owner != corporation }
            corporation.run(tick, sea, otherShips)
        }

        for (ship in allShips) {
            ship.arrivedToHarborThisTick = false
        }
    }

    /**
     * handles the drift garbage logic
     */
    private fun driftGarbage() {
        val garbageToList: MutableMap<Tile, MutableList<Garbage?>> = mutableMapOf()

        sea.tiles
            .filterIsInstance<DeepOcean>().sortedBy { it.id }
            .forEach { tile ->
                garbageDriftHelper(tile, garbageToList)
            }

        garbageToList.forEach { (tile, garbageList) ->
            garbageList.toList().forEach { garbage ->
                if (garbage != null) {
                    tile.addGarbage(garbage)
                }
            }
        }

        sea.tiles.filterIsInstance<DeepOcean>().forEach {
                tile ->
            tile.amountOfGarbageDriftedThisTick = 0
        }
    }

    private fun garbageDriftHelper(
        currentTile: DeepOcean,
        garbageToList: MutableMap<Tile, MutableList<Garbage?>>,
    ) {
        val garbageList = currentTile.garbage.sortedBy { it.id }
        for (garbage in garbageList) {
            val current = currentTile.getCurrent()
            if (current == null) {
                return
            } else {
                val targetTile = getValidTile(currentTile, current.speed / TEN, current.direction)
                var garbageTile: Pair<Tile, Garbage>? = null
                if (targetTile != null && targetTile != currentTile) {
                    garbageTile = garbage.drift(currentTile, targetTile, current)
                    /**
                     * I GUESS THIS SHOULD BE ADDED HERE
                     *currentTile.garbage.remove(garbageTile.second)
                     */
                }
                if (garbageTile != null) {
                    garbageToList.getOrPut(garbageTile.first) { mutableListOf() }.add(garbageTile.second)
                }
            }
        }
    }

    private fun getValidTile(currentTile: DeepOcean, distance: Int, direction: Direction): Tile? {
        var targetTile = currentTile.getTileInDirection(distance, direction)

        if (distance == 0) {
            targetTile = currentTile
        } else {
            for (i in 1..distance) {
                currentTile.getTileInDirection(i, direction)
                    ?: return currentTile.getTileInDirection(i - 1, direction)
            }
        }
        return targetTile
    }

    /**
     * handles the drift ships logic
     */
    private fun driftShips() {
        /**
         * This is wrong, drifts should be logged from the lowest tile id to the highest*/
        val tiles = sea.tiles
        val deepOceanTiles = tiles.filterIsInstance<DeepOcean>().filter { it.getCurrent() != null }
        val deepOceanTilesHavingShips: PriorityQueue<DeepOcean> = PriorityQueue<DeepOcean>(compareBy { it.id })
        val shipsInDeepOcean: MutableSet<Ship> = mutableSetOf()
        for (corporation in corporations) {
            deepOceanTiles
                .filter { tile -> corporation.ownedShips.any { it.position == tile } }
                .sortedBy { it.id }.forEach {
                        deepOcean ->
                    deepOceanTilesHavingShips.add(deepOcean)
                }
            corporation.ownedShips.forEach { ship ->
                if (ship.position in deepOceanTilesHavingShips) {
                    shipsInDeepOcean.add(ship)
                }
            }
        }
        for (tile in deepOceanTilesHavingShips) {
            shipsInDeepOcean.filter { ship -> ship.position == tile }.sortedBy { it.id }.forEach {
                it.drift()
            }
        }
        for (tile in deepOceanTiles) {
            tile.amountOfShipsDriftedThisTick = 0
        }
    }

    /**
     * iterates over all events and call actUponTick on them
     */
    private fun processEvents() {
        for (event in allEvents.sortedBy { it.id }) {
            event.actUponTick(tick, corporations)
        }
    }

    /**
     * starts new tasks and updates active tasks
     */
    private fun processTasks() {
        val tasks = corporations.map { it.tasks }.flatten().sortedBy { it.id }
        val tasksToBeAdded = tasks.filter { it.tick == tick }
        tasksToBeAdded.forEach { task ->
            activeTasks.putIfAbsent(task, true)
        }
        for (task in tasksToBeAdded) {
            task.actUponTick(tick)
        }
    }
}
