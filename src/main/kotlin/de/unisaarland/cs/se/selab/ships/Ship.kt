package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.tiles.Tile

class Ship (
        private val id: Int,
        private val name: String,
        private val owner: Corporation,
        private val maxVelocity: Int,
        private val acceleration: Int,
        var pos: Tile,
        private var fuelCapacity: Int,
        private var fuelConsumption: Int,
        var capabilities: List<ShipCapability>,
        private var hasTaskAssigned: Boolean = false
        ) {

    // does the velocity, acceleration etc. change when a ship gets new capability ?
    init {
        require(capabilities.isNotEmpty()) { "Ship must have at least one capability" }

        val defaultCapability = capabilities.first()
        when (defaultCapability) {
            is ScoutingShip -> {
                require(maxVelocity <= 100) { "Scouting ships must have a max velocity greater than 100, but was $maxVelocity" }
            }
            is CollectingShip -> {
                require(maxVelocity <= 50) { "Collecting ships must have a max velocity less than or equal to 50, but was $maxVelocity" }
            }
            is CoordinatingShip -> {
                require(maxVelocity <= 50) { "Scouting ships must have a max velocity greater than 50, but was $maxVelocity" }
            }
        }
    }

    public fun getOwner(): Corporation{
        return this.owner;
    }
     /**
     * Call: when the ship is on the harbor
     * Logic: the ship has to max its fuelCapacity
     */
     /**
     * TODO: Implement.
     */
    public fun refuel(): Unit{
        TODO("")
    }
    /**
     *  Call: when a ship is on a deepOcean tile
     *  Logic: the ship has to check if its tile has a current,
     *  if so do the logic of drifting
    */
    /**
     * TODO: Implement.
     */
    public fun drift(): Unit{
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
    public fun move(path: List<Tile>): Unit{
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
    public fun isFuelSufficient(pathLength: Int): Boolean{
        TODO("")
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
