package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.logger.Logger
import de.unisaarland.cs.se.selab.logger.LoggerCorporationAction
import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
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
    private var currentVelocity = 0

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
            handleCurrentDrift(deepOcean, current)
        }
    }

    private fun handleCurrentDrift(tile: DeepOcean, current: Current) {
        val speed = current.speed
        val direction = current.direction
        val intensity = current.intensity
        if (intensity > tile.amountOfShipsDriftedThisTick) {
            val desTile = this.position.getTileInDirection(speed / TEN, direction)
            if (desTile != null) {
                this.position = desTile
            }
            tile.amountOfShipsDriftedThisTick += 1
            Logger.logCurrentDriftShip(id, tile.id, position.id)
        }
    }

    /**
     * Call: when a ship is the closest one to the garbage or when the ship has to return to the harbor
     * Logic: the ship gets a path (a list of tiles from destination to ship), has to reverse path and move along it
     * the ship moves along the path as long as it can
     */
    fun move(path: List<Tile>) {
        // acceleration
        if (currentVelocity < maxVelocity) {
            currentVelocity += acceleration
            if (currentVelocity > maxVelocity) {
                currentVelocity = maxVelocity
            }
        }
        // the distance the ship can traverse
        val distanceLength = currentVelocity / TEN
        val desTile: Tile
        if (path.size > distanceLength) {
            desTile = path[distanceLength]
            consumedFuel += distanceLength * TEN * fuelConsumption
            LoggerCorporationAction.logShipMovement(id, currentVelocity, desTile.id)
        } else {
            desTile = path.last()
            consumedFuel += path.size * TEN * fuelConsumption
            LoggerCorporationAction.logShipMovement(id, currentVelocity, desTile.id)
        }
        if (desTile == path.last()) {
            currentVelocity = 0
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
        moveUninterrupted(destinationPath)
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
        } else {
            val startIndex = pathToHarbor.indexOf(this.position)
            val lastIndex = pathToHarbor.size - 1
            destinationPath = pathToHarbor.subList(startIndex, lastIndex)
        }
    }

    /**
     * Checks its default garbage type and returns true if it can collect depending on the type
     * if plastic then true if it can collect whole amount
     * if oil then true if it can collect some of the amount
     * */
    fun isCapacitySufficient(garbage: List<Garbage>): Boolean {
        val result: Boolean
        val capability = this.capabilities.first() as CollectingShip

        val oil = garbage.filter { it.type == GarbageType.OIL }
        val plastic = garbage.filter { it.type == GarbageType.PLASTIC }
        val chemicals = garbage.filter { it.type == GarbageType.CHEMICALS }
        val defaultType = capability.auxiliaryContainers.first().garbageType

        if (defaultType == GarbageType.OIL && oil.isNotEmpty()) {
            result = capability.hasOilCapacity()
        } else if (defaultType == GarbageType.PLASTIC && plastic.isNotEmpty()) {
            val amount = plastic.sumOf { it.amount }
            val collectable = capability.hasPlasticCapacity()
            result = amount <= collectable
        } else if (defaultType == GarbageType.CHEMICALS && chemicals.isNotEmpty()) {
            result = capability.hasChemicalsCapacity()
        } else {
            val listOfTypes = capability.garbageTypes()
            val garbageList = garbage.filter { listOfTypes.contains(it.type) }
            if (garbageList.isNotEmpty()) {
                result = handleSecondaryContainers(garbageList, capability)
            } else {
                result = false
            }
        }
        return result
    }

    private fun handleSecondaryContainers(garbage: List<Garbage>, capability: CollectingShip): Boolean {
        val result: Boolean
        val oilGarbage = garbage.filter { it.type == GarbageType.OIL }
        val plasticGarbage = garbage.filter { it.type == GarbageType.PLASTIC }
        val chemicalsGarbage = garbage.filter { it.type == GarbageType.CHEMICALS }
        result = if (oilGarbage.isNotEmpty()) {
            capability.hasOilCapacity()
        } else if (chemicalsGarbage.isNotEmpty()) {
            capability.hasChemicalsCapacity()
        } else {
            capability.hasPlasticCapacity() >= plasticGarbage.sumOf { it.amount }
        }
        return result
    }
}
