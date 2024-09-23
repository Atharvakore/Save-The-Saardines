package de.unisaarland.cs.se.selab

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.events.Event
import de.unisaarland.cs.se.selab.logger.Logger
import de.unisaarland.cs.se.selab.logger.LoggerEventsAndTasks
import de.unisaarland.cs.se.selab.logger.LoggerStatistics
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.Land
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.TEN
import de.unisaarland.cs.se.selab.tiles.Tile

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
            corporation.run(sea, otherShips)
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
    }

    private fun garbageDriftHelper(currentTile: DeepOcean, garbageToList: MutableMap<Tile, MutableList<Garbage?>>) {
        val garbageList = currentTile.garbage
        for (garbage in garbageList.sortedBy { it.id }) {
            val current = currentTile.getCurrent()
            if (current != null) {
                val targetTile = currentTile.getTileInDirection(current.speed / TEN, current.direction)
                if (targetTile != null && targetTile !is Land) {
                    val g = garbage.drift(currentTile, targetTile, current)
                    garbageToList.getOrPut(currentTile) { mutableListOf() }.add(g)
                }
            } else {
                garbageToList.remove(currentTile)
            }
        }
    }

    /**
     * handles the drift ships logic
     */
    private fun driftShips() {
        val tiles = sea.tiles
        val deepOceanTiles = tiles.filterIsInstance<DeepOcean>()

        for (corporation in corporations) {
            val deepOceanTilesWithShips = deepOceanTiles
                .filter { tile -> corporation.ownedShips.any { it.position == tile } }
                .sortedBy { it.id }
            for (tile in deepOceanTilesWithShips) {
                val shipsOnTile = corporation.ownedShips
                    .filter { it.position == tile }
                    .sortedBy { it.id }
                for (ship in shipsOnTile) {
                    ship.drift()
                }
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
            event.actUponTick(tick)
        }
    }

    /**
     * starts new tasks and updates active tasks
     */
    private fun processTasks() {
        val tasks = collectActiveTasks().sortedBy { it.id }

        for (task in tasks) {
            task.actUponTick(tick)
            if (task.tick == this.tick) {
                LoggerEventsAndTasks.logTaskAddedToShip(task.id, task, task.taskShip.id, task.getGoal().id)
            }
        }
    }

    private fun collectActiveTasks(): List<Task> {
        val allTasks = mutableListOf<Task>()
        for (corporation in corporations) {
            allTasks.addAll(corporation.getActiveTasks(tick))
        }
        return allTasks
    }
}
