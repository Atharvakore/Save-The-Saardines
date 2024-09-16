package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.tiles.Tile

abstract class Ship (
    private val id: Int,
    private val name: String,
    private val owner: Corporation,
    private val maxVelocity: Int,
    private val acceleration: Int,
    var pos: Tile,
    private var fuelCapacity: Int,
    private var fuelConsumption: Int,
    var capabilities: List<ShipCapability>,
    private var hasTaskAssigned: Boolean = false,
    private var currentFuelCapacity: Int = 0
) {
    init {
        require(capabilities.isNotEmpty()) { "Ship must have at least one capability" }

        val defaultCapability = capabilities.first()
        when (defaultCapability) {
            is ScoutingShip -> {
                require(maxVelocity in 10..100) { "Scouting ships must have a max velocity greater than 100, but was $maxVelocity" }
                require(acceleration in 5..25)
                require(fuelCapacity in 3000..10000)
                require(fuelConsumption in 7..10)
            }

            is CoordinatingShip -> {
                require(maxVelocity in 10..50) { "Scouting ships must have a max velocity greater than 50, but was $maxVelocity" }
                require(acceleration in 5..15)
                require(fuelCapacity in 3000..5000)
                require(fuelConsumption in 5..7)
            }

            is CollectingShip -> {
                require(maxVelocity in 10..50) { "Collecting ships must have a max velocity less than or equal to 50, but was $maxVelocity" }
                require(acceleration in 5..10)
                require(fuelCapacity in 3000..5000)
                require(fuelConsumption in 5..9)
            }
        }
    }

    fun getOwner(): Corporation {
        return this.owner;
    }
    /**
     * Call: when the ship is on the harbor
     * Logic: the ship has to max its fuelCapacity
     */
    /**
     * TODO: Implement.
     */
    fun refuel(): Unit {
        this.currentFuelCapacity = 0
    }
    /**
     *  Call: when a ship is on a deepOcean tile
     *  Logic: the ship has to check if its tile has a current,
     *  if so do the logic of drifting
     */
    /**
     * TODO: Implement.
     */
    fun drift(): Unit {
        TODO("")
    }
    /**
     * Call: when a ship is the closest one to the garbage or when the ship has to return to the harbor
     * Logic: the ship gets a path (a list of tiles from destination to ship), has to reverse path and move along it
     * the ship moves along the path as long as it can
     *
     * @param path a list of tiles between destination and ship
     */
    /**
     * TODO: Implement.
     */
    fun move(path: List<Tile>): Unit {
        TODO("")
    }
    /**
     * Call: when corp checks if the ship can be sent
     * Logic: gets length of the path the ship has to traverse
     * check if it can traverse it
     *
     * @param  length of path
     * @return if the ship can complete this path
     */
    /**
     * TODO: Implement.
     */
    fun isFuelSufficient(pathLength: Int): Boolean {
        val neededFuel = fuelConsumption * pathLength * 10
        return neededFuel <= fuelCapacity - currentFuelCapacity
    }
    /**
     * Call: when a task is done, and the reward needs to be applied
     * Logic: adds a capability to the ship and handles the adding logic
     *
     * @param a ship capability
     */
    /**
     * TODO: Implement.
     */
    public fun addCapability(capability: ShipCapability): Unit {
        TODO("")
    }

}
