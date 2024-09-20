package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.logger.Logger
import de.unisaarland.cs.se.selab.logger.LoggerCorporationAction
import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Tile

/**
 * class representing a ship
 */
class Ship(
    val id: Int,
    private val maxVelocity: Int,
    private val acceleration: Int,
    private var fuelCapacity: Int,
    private var fuelConsumption: Int,
    var capabilities: MutableList<ShipCapability>,
) {
    lateinit var position: Tile
    lateinit var name: String
    lateinit var owner: Corporation
    private var consumedFuel: Int = 0
    var hasTaskAssigned: Boolean = false
    private var destinationPath = emptyList<Tile>()

    /**
     * Call: when the ship is on the harbor
     * Logic: the ship has to max its fuelCapacity
     */
    fun refuel() {
        this.consumedFuel = 0
        LoggerCorporationAction.logRefuelingShip(id, position.id)
    }

    /**
     *  Call: when a ship is on a deepOcean tile
     *  Logic: the ship has to check if its tile has a current,
     *  if so do the logic of drifting
     */
    fun drift() {
        val deepOcean = this.position as? DeepOcean
        val current = deepOcean?.getCurrent()
        if (current != null) {
            handleCurrentDrift(deepOcean)
        }
    }

    private fun handleCurrentDrift(tile: DeepOcean) {
        val current: Current? = tile.getCurrent()
        if (current != null) {
            val speed = current.speed
            val direction = current.direction
            val desTile = this.position.getTileInDirection(speed / TEN + 1, direction)
            if (desTile != null) {
                this.position = desTile
            }
            Logger.logCurrentDriftShip(id, tile.id, position.id)
        }
    }

    /**
     * Call: when a ship is the closest one to the garbage or when the ship has to return to the harbor
     * Logic: the ship gets a path (a list of tiles from destination to ship), has to reverse path and move along it
     * the ship moves along the path as long as it can
     *
     * distance = (velocity^2 / 2 * acceleration)
     */
    fun move(path: List<Tile>) {
        // the distance the ship can traverse
        val distanceLength = maxVelocity * maxVelocity / (2 * acceleration) / TEN
        var desTile = this.position
        if (path.size >= distanceLength) {
            desTile = path[distanceLength - 1]
            consumedFuel += distanceLength * TEN * fuelConsumption
            LoggerCorporationAction.logShipMovement(id, acceleration, desTile.id)
        } else {
            desTile = path.last()
            consumedFuel += path.size * TEN * fuelConsumption
            LoggerCorporationAction.logShipMovement(id, acceleration, desTile.id)
        }
        this.position = desTile
    }

    /**
     * Call: when corp checks if the ship can be sent
     * Logic: gets length of the path the ship has to traverse
     * check if it can traverse it
     *
     * @param pathLength of path
     * @return if the ship can complete this path
     */
    fun isFuelSufficient(pathLength: Int): Boolean {
        val neededFuel = fuelConsumption * pathLength * TEN
        return neededFuel <= fuelCapacity - consumedFuel
    }

    /**
     * Call: when a task is done, and the reward needs to be applied
     * Logic: adds a capability to the ship and handles the adding logic
     *
     * @param capability ship capability
     */
    fun addCapability(capability: ShipCapability) {
        capabilities.add(capability)
    }

    /**
     * complete the movement of the ship along the destination path
     * if it has an assigned task
     * */
    fun tickTask() {
        val lastTileIndex = destinationPath.size - 1
        val reachedTileIndex = destinationPath.indexOf(this.position) + 1
        destinationPath = destinationPath.subList(reachedTileIndex, lastTileIndex)
        move(destinationPath)
        if (this.position == destinationPath.last()) {
            hasTaskAssigned = false
            destinationPath = emptyList()
        }
    }

    /**
     * set destination for the ship
     * and store the path until its completed
     * */
    fun moveUninterrupted(pathToHarbor: List<Tile>) {
        hasTaskAssigned = true
        move(pathToHarbor)
        if (this.position == pathToHarbor.last()) {
            hasTaskAssigned = false
            destinationPath = emptyList()
        }
    }
}
