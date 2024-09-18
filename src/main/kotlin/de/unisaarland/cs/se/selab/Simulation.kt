package de.unisaarland.cs.se.selab

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.events.Event
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Sea

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
        while (tick <= maxTick) {
            tick()
            tick++
        }
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

        for (corporation in corporations) {
            val otherShips = allShips.filter { it.owner != corporation }
            corporation.run(otherShips, tick)
        }
    }

    /**
     * handles the drift garbage logic
     */
    private fun driftGarbage() {
        val tiles = sea.tiles
        val deepOceanTiles = tiles.filterIsInstance<DeepOcean>()

        for (tile in deepOceanTiles) {
            val garbageList = tile.garbage
            for (garbage in garbageList) {
                garbage.drift(tile)
            }
        }
    }

    /**
     * handles the drift ships logic
     */
    private fun driftShips() {
        val tiles = sea.tiles
        val deepOceanTiles = tiles.filterIsInstance<DeepOcean>()

        val shipsOnDOTiles = mutableListOf<Ship>()
        for (corporation in corporations) {
            shipsOnDOTiles.addAll(corporation.ownedShips.filter { deepOceanTiles.contains(it.position) })
        }

        for (ship in shipsOnDOTiles) {
            ship.drift()
        }
    }

    /**
     * iterates over all events and call actUponTick on them
     */
    private fun processEvents() {
        for (event in allEvents) {
            event.actUponTick(tick)
        }
    }

    /**
     * starts new tasks and updates active tasks
     */
    private fun processTasks() {
        val tasks = collectActiveTasks()

        for (task in tasks) {
            task.actUponTick(tick)
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
