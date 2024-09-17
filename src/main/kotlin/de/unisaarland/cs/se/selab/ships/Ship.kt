package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Current

/**
 * class representing a ship
 */
abstract class Ship(
    private val shipInformation: ShipInformation,
    private val maxVelocity: Int,
    private val acceleration: Int,
    private var fuelCapacity: Int,
    private var fuelConsumption: Int,
    var capabilities: MutableList<ShipCapability>,
) {

    init {
        require(capabilities.isNotEmpty())

        val defaultCapability = capabilities.first()
        when (defaultCapability) {
            is ScoutingShip -> {
                require(maxVelocity in MIN_VELOCITY_SCOUTING..MAX_VELOCITY_SCOUTING)
                require(acceleration in MIN_ACCELERATION_SCOUTING..MAX_ACCELERATION_SCOUTING)
                require(fuelCapacity in MIN_FUEL_CAPACITY_SCOUTING..MAX_FUEL_CAPACITY_SCOUTING)
                require(fuelConsumption in MIN_FUEL_CONSUMPTION_SCOUTING..MAX_FUEL_CONSUMPTION_SCOUTING)
            }

            is CoordinatingShip -> {
                require(maxVelocity in MIN_VELOCITY_COORDINATING..MAX_VELOCITY_COORDINATING)
                require(acceleration in MIN_ACCELERATION_COORDINATING..MAX_ACCELERATION_COORDINATING)
                require(fuelCapacity in MIN_FUEL_CAPACITY_COORDINATING..MAX_FUEL_CAPACITY_COORDINATING)
                require(fuelConsumption in MIN_FUEL_CONSUMPTION_COORDINATING..MAX_FUEL_CONSUMPTION_COORDINATING)
            }

            is CollectingShip -> {
                require(maxVelocity in MIN_VELOCITY_COLLECTING..MAX_VELOCITY_COLLECTING)
                require(acceleration in MIN_ACCELERATION_COLLECTING..MAX_ACCELERATION_COLLECTING)
                require(fuelCapacity in MIN_FUEL_CAPACITY_COLLECTING..MAX_FUEL_CAPACITY_COLLECTING)
                require(fuelConsumption in MIN_FUEL_CONSUMPTION_COLLECTING..MAX_FUEL_CONSUMPTION_COLLECTING)
            }
        }
    }
    private var consumedFuel: Int = 0
    var hasTaskAssigned: Boolean = false
    private var destinationPath = emptyList<Tile>()

    /**
     * return owner corporation
     */
    fun getOwner(): Corporation {
        return this.shipInformation.owner
    }

    /**
     * return ship current tile
     */
    fun getPos(): Tile {
        return this.shipInformation.pos
    }

    /**
     * Call: when the ship is on the harbor
     * Logic: the ship has to max its fuelCapacity
     */
    fun refuel() {
        this.consumedFuel = 0
    }

    /**
     *  Call: when a ship is on a deepOcean tile
     *  Logic: the ship has to check if its tile has a current,
     *  if so do the logic of drifting
     */
    fun drift() {
        val deepOcean = getPos() as? DeepOcean
        val current = deepOcean?.getCurrent()
        if (current != null) {
            handleCurrentDrift(current)
        }
    }

    private fun handleCurrentDrift(current: Current) {
        val speed = current.speed
        val direction = current.direction

        if (direction != null) {
            val desTile = getPos().getTileInDirection(speed / TEN, direction)
            if (desTile != null) {
                this.shipInformation.pos = desTile
            }
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
        val pathShipToTile = path.reversed()
        // the distance the ship can traverse
        val distanceLength = (maxVelocity * maxVelocity / (2 * acceleration)) / TEN
        var desTile = getPos()
        if (pathShipToTile.size >= distanceLength){
            desTile = pathShipToTile[distanceLength]
            consumedFuel += distanceLength * TEN * fuelConsumption
        } else {
            desTile = pathShipToTile.last()
            consumedFuel += pathShipToTile.size * TEN * fuelConsumption
        }
        this.shipInformation.pos = desTile
    }

    /**
     * Call: when corp checks if the ship can be sent
     * Logic: gets length of the path the ship has to traverse
     * check if it can traverse it
     *
     * @param  pathLength of path
     * @return if the ship can complete this path
     */
    fun isFuelSufficient(pathLength: Int): Boolean {
        val neededFuel = fuelConsumption * pathLength * TEN
        return neededFuel <= fuelCapacity - consumedFuel
    }

    /**
     * complete the movement of the ship along the destination path
     * if it has an assigned task
     * */
    fun tickTask(){
        val lastTileIndex = destinationPath.size - 1
        val reachedTileIndex = destinationPath.indexOf(getPos())
        destinationPath = destinationPath.subList(reachedTileIndex, lastTileIndex)
        move(destinationPath)
        if (getPos() == destinationPath.last()){
            hasTaskAssigned = false
            destinationPath = emptyList()
        }
    }

    /**
     * set destination for the ship
     * and store the path until its completed
     * */
    fun moveUninterrupted(pathToHarbor: List<Tile>){
        hasTaskAssigned = true
        move(pathToHarbor)
        if (getPos() == pathToHarbor.last()){
            hasTaskAssigned = false
            destinationPath = emptyList()
        }
    }
}
