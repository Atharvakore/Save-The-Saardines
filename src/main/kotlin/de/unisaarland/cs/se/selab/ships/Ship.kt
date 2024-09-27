package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.corporation.Helper
import de.unisaarland.cs.se.selab.logger.Logger
import de.unisaarland.cs.se.selab.logger.LoggerCorporationAction
import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile

/**
 * class representing a ship
 */
open class Ship(
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
    var destinationPath = emptyList<Tile>()
    var currentVelocity = 0
    var refueling = false
    var arrivedToHarborThisTick = false
    var movedThisTick: MovementTuple = MovementTuple(false, -1, -1, -1)
    var unloading = false

    /**
     * ship has at least on full container
     */
    fun shouldUnload(): Boolean {
        return this.capabilities.filterIsInstance<CollectingShip>()
            .any { it.auxiliaryContainers.any { container -> container.shouldUnload() } }
    }

    /**
     * Call: when the ship is on the harbor
     * Logic: the ship has to max its fuelCapacity
     */
    fun refuel() {
        if (!arrivedToHarborThisTick) {
            refueling = false
            currentVelocity = 0
            this.consumedFuel = 0
            LoggerCorporationAction.logRefuelingShip(id, position.id)
        }
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
            handleCurrentDrift(deepOcean, current)
        }
    }

    private fun handleCurrentDrift(tile: DeepOcean, current: Current) {
        val speed = current.speed
        val direction = current.direction
        val intensity = current.intensity
        if (intensity > tile.amountOfShipsDriftedThisTick) {
            var i = 0
            var desTile: Tile? = null
            // ANOTHER CHECK FOR THE VALID PATH SHOULD BE ADDED HERE, SAME AS FOR GARBAGE DRIFT (DONE, NOT TESTED YET)
            while (i <= speed && this.position.getTileInDirection(i / SPEED_LENGTH, direction) != null) {
                desTile = this.position.getTileInDirection(i / SPEED_LENGTH, direction)
                i++
            }
            if (desTile != null && desTile != this.position) {
                this.position = desTile
                tile.amountOfShipsDriftedThisTick += 1
                Logger.logCurrentDriftShip(id, tile.id, position.id)
            }
        }
    }

    /**
     * How many tiles can we traverse in one tick
     */
    fun speed(): Int {
        return minOf(currentVelocity + acceleration, maxVelocity) / SPEED_LENGTH
    }

    /**
     * Call: when a ship is the closest one to the garbage or when the ship has to return to the harbor
     * Logic: the ship gets a path (a list of tiles from destination to ship), has to reverse path and move along it
     * the ship moves along the path as long as it can
     */
    fun move(path: List<Tile>) {
        // acceleration
        currentVelocity = minOf(currentVelocity + acceleration, maxVelocity)
        val oldFuel = consumedFuel

        // the distance the ship can traverse
        val distanceLength = currentVelocity / SPEED_LENGTH
        val desTile: Tile
        if (path.size > distanceLength) {
            desTile = path[distanceLength]
            consumedFuel += distanceLength * SPEED_LENGTH * fuelConsumption
            if (desTile != this.position && consumedFuel <= fuelCapacity) {
                this.movedThisTick = MovementTuple(true, id, currentVelocity, desTile.id)
                this.position = desTile
            } else {
                consumedFuel = oldFuel
            }
        } else {
            desTile = path.last()
            consumedFuel += (path.size - 1) * SPEED_LENGTH * fuelConsumption
            if (desTile != this.position && consumedFuel <= fuelCapacity) {
                this.movedThisTick = MovementTuple(true, id, currentVelocity, desTile.id)
                this.position = desTile
                currentVelocity = 0
            } else {
                consumedFuel = oldFuel
            }
        }
    }

    /**
     * Call: when corp checks if the ship can be sent
     * Logic: gets length of the path the ship has to traverse
     * check if it can traverse it
     *
     * @param pathLength of path
     * @return if the ship can complete this path
     */
    fun isFuelSufficient(pathLength: Int, ownedHarbors: List<Shore>, targetTile: Tile): Boolean {
        var shortestPathToHarbor: List<Tile>? = Helper().findClosestHarbor(targetTile, ownedHarbors)
        if (shortestPathToHarbor == null) {
            shortestPathToHarbor = mutableListOf()
        }
        val fullPath = minOf(pathLength, speed()) + shortestPathToHarbor.size
        val neededFuel = fuelConsumption * fullPath * SPEED_LENGTH
        val result = neededFuel <= fuelCapacity - consumedFuel
        if (!result) {
            this.refueling = true
        }
        return result
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
    fun tickTask(isTask: Boolean) {
        moveUninterrupted(destinationPath, isTask, this.refueling, this.unloading)
    }

    /**
     * set destination for the ship
     * and store the path until its completed
     * */
    fun moveUninterrupted(pathToHarbor: List<Tile>?, isTask: Boolean, isRefuel: Boolean, isUnloading: Boolean) {
        this.refueling = isRefuel
        this.unloading = isUnloading
        hasTaskAssigned = isTask
        if (pathToHarbor.isNullOrEmpty() || this.position == pathToHarbor.last()) {
            return
        }
        move(pathToHarbor)
        if (this.position == pathToHarbor.last()) {
            hasTaskAssigned = false
            destinationPath = emptyList()
            if (this.owner.ownedHarbors.contains(this.position)) {
                arrivedToHarborThisTick = true
            }
        } else {
            val startIndex = pathToHarbor.indexOf(this.position)
            val lastIndex = pathToHarbor.size
            destinationPath = pathToHarbor.subList(startIndex, lastIndex)
        }
    }

    /**
     * Checks its garbage types and returns true if it can collect depending on the type
     * */
    fun isCapacitySufficient(garbage: List<Garbage>): Boolean {
        var result = false
        val capabilities = this.capabilities.filterIsInstance<CollectingShip>()

        if (capabilities.isEmpty() || checkContainerFull()) {
            return false
        }

        val oilContainers: MutableList<Container> = mutableListOf()
        val plasticContainers: MutableList<Container> = mutableListOf()
        val chemicalsContainers: MutableList<Container> = mutableListOf()

        capabilities.forEach { cap ->
            oilContainers.addAll(cap.auxiliaryContainers.filter { it.garbageType == GarbageType.OIL })
            plasticContainers.addAll(cap.auxiliaryContainers.filter { it.garbageType == GarbageType.PLASTIC })
            chemicalsContainers.addAll(cap.auxiliaryContainers.filter { it.garbageType == GarbageType.CHEMICALS })
        }

        val collectableOil = oilContainers.sumOf { it.getGarbageCapacity() - it.garbageLoad }
        val collectablePlastic = plasticContainers.sumOf { it.getGarbageCapacity() - it.garbageLoad }
        val collectableChemicals = chemicalsContainers.sumOf { it.getGarbageCapacity() - it.garbageLoad }

        for (g in garbage) {
            if (g.type == GarbageType.OIL && collectableOil > 0 && g.amount > 0) {
                result = true
            } else if (g.type == GarbageType.PLASTIC && collectablePlastic > 0 && g.amount > 0) {
                result = true
            } else if (g.type == GarbageType.CHEMICALS && collectableChemicals > 0 && g.amount > 0) {
                result = true
            }
        }
        return result
    }

    /**
     * returns true if any container in the ship is full
     * */
    private fun checkContainerFull(): Boolean {
        val collectingShip = capabilities.filterIsInstance<CollectingShip>()

        for (cap in collectingShip) {
            val containers = cap.auxiliaryContainers
            for (container in containers) {
                if (container.garbageLoad == container.getGarbageCapacity()) {
                    return true
                }
            }
        }

        return false
    }

    /**
     * returns if the ship can collect
     */
    fun hasCollectingCapability(): Boolean {
        return capabilities.filterIsInstance<CollectingShip>().isNotEmpty()
    }

    /**
     * returns weither the ship needs to unload garbage
     */
    fun needsToUnload(): Boolean {
        return capabilities.filterIsInstance<CollectingShip>().map { it.auxiliaryContainers }.flatten()
            .any { it.garbageLoad == it.getGarbageCapacity() }
    }
}
