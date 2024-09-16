package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Current

abstract class Ship(
    private val id: Int,
    private val owner: Corporation,
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
                require(maxVelocity in 10..100)
                require(acceleration in 5..25)
                require(fuelCapacity in 3000..10000)
                require(fuelConsumption in 7..10)
            }

            is CoordinatingShip -> {
                require(maxVelocity in 10..50)
                require(acceleration in 5..15)
                require(fuelCapacity in 3000..5000)
                require(fuelConsumption in 5..7)
            }

            is CollectingShip -> {
                require(maxVelocity in 10..50)
                require(acceleration in 5..10)
                require(fuelCapacity in 3000..5000)
                require(fuelConsumption in 5..9)
            }
        }
    }

    private var name: String = ""
    private var pos: Tile? = null
    private var consumedFuel: Int = 0
    var hasTaskAssigned: Boolean = false

    constructor(
        id: Int,
        owner: Corporation,
        maxVelocity: Int,
        acceleration: Int,
        fuelCapacity: Int,
        fuelConsumption: Int,
        capabilities: MutableList<ShipCapability>,
        name: String,
        pos: Tile? = null
    ) : this(id, owner, maxVelocity, acceleration, fuelCapacity, fuelConsumption, capabilities) {
        this.name = name
        this.pos = pos
    }

    fun getOwner(): Corporation {
        return this.owner;
    }

    fun getPos(): Tile? {
        return this.pos
    }

    /**
     * Call: when the ship is on the harbor
     * Logic: the ship has to max its fuelCapacity
     */
    fun refuel(): Unit {
        this.consumedFuel = 0
    }

    /**
     *  Call: when a ship is on a deepOcean tile
     *  Logic: the ship has to check if its tile has a current,
     *  if so do the logic of drifting
     */
    fun drift(): Unit {
        val deepOcean = this.pos as? DeepOcean
        val current = deepOcean?.getCurrent()
        if (current != null) {
            handleCurrentDrift(current)
        }
    }

    private fun handleCurrentDrift(current: Current): Unit {
        val speed = current.speed
        val direction = current.direction

        if (speed != null && direction != null) {
            val desTile = this.pos?.getTileInDirection(speed / 10, direction)
            if (desTile != null) {
                this.pos = desTile
            }
        }
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
    fun isFuelSufficient(pathLength: Int): Boolean {
        val neededFuel = fuelConsumption * pathLength * 10
        return neededFuel <= fuelCapacity - consumedFuel
    }

    /**
     * Call: when a task is done, and the reward needs to be applied
     * Logic: adds a capability to the ship and handles the adding logic
     *
     * @param a ship capability
     */

    public fun addCapability(capability: ShipCapability): Unit {
        capabilities.add(capability)
    }
}
