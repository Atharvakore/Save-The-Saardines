package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.corporation.Helper
import de.unisaarland.cs.se.selab.logger.LoggerCorporationAction
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Tile

/**
 * class representing the collecting ship capability
 */
class CollectingShip(
    var auxiliaryContainers: MutableList<Container>
) : ShipCapability {
    var unloading: Boolean = false

    /**
     * unloads all the containers of the ship
     */
    fun unload(ship: Ship): Boolean {
        if (ship.arrivedToHarborThisTick || !unloading) {
            return false
        }
        val amountOfPlastic: Int = auxiliaryContainers
            .filter { it.garbageType == GarbageType.PLASTIC }
            .sumOf { it.garbageLoad }
        if (amountOfPlastic > 0) {
            LoggerCorporationAction.logUnloadShip(ship.id, amountOfPlastic, GarbageType.PLASTIC, ship.position.id)
        }
        val amountOfOil: Int = auxiliaryContainers
            .filter { it.garbageType == GarbageType.OIL }
            .sumOf { it.garbageLoad }
        if (amountOfOil > 0) {
            LoggerCorporationAction.logUnloadShip(ship.id, amountOfOil, GarbageType.OIL, ship.position.id)
        }
        val amountOfChemicals: Int = auxiliaryContainers
            .filter { it.garbageType == GarbageType.CHEMICALS }
            .sumOf { it.garbageLoad }
        if (amountOfChemicals > 0) {
            LoggerCorporationAction.logUnloadShip(ship.id, amountOfChemicals, GarbageType.CHEMICALS, ship.position.id)
        }
        for (container in auxiliaryContainers) {
            container.giveGarbage()
        }
        this.unloading = false
        ship.currentVelocity = 0
        return true
    }

    /**
     *
     * checks if the ship can collect any amount of oil
     * */
    fun hasOilCapacity(): Boolean {
        val oilContainers = auxiliaryContainers.filter { it.garbageType == GarbageType.OIL }
        if (oilContainers.isEmpty()) {
            return false
        } else {
            var oilCapacity = 0
            for (container in oilContainers) {
                oilCapacity += container.getGarbageCapacity() - container.garbageLoad
            }
            return oilCapacity > 0
        }
    }

    /**
     *
     * checks if the ship can collect any amount of chemicals
     * */
    fun hasChemicalsCapacity(): Boolean {
        val chemicalsContainers = auxiliaryContainers.filter { it.garbageType == GarbageType.CHEMICALS }
        if (chemicalsContainers.isEmpty()) {
            return false
        } else {
            var chemicalsCapacity = 0
            for (container in chemicalsContainers) {
                chemicalsCapacity += container.getGarbageCapacity() - container.garbageLoad
            }
            return chemicalsCapacity > 0
        }
    }

    /**
     * checks the amount of plastic the ship can collect
     * */
    fun hasPlasticCapacity(): Int {
        val plasticContainers = auxiliaryContainers.filter { it.garbageType == GarbageType.PLASTIC }
        if (plasticContainers.isEmpty()) {
            return 0
        } else {
            var plasticCapacity = 0
            for (container in plasticContainers) {
                plasticCapacity += container.getGarbageCapacity() - container.garbageLoad
            }
            return plasticCapacity
        }
    }

    /**
     * Call: when the corporation is calling on its ships to collect the garbage
     * Logic: checks the garbage of its tile, collects it if it can
     */
    fun collectGarbageFromCurrentTile(ship: Ship, shipsOnTheTile: List<Ship>) {
        val currentTile: Tile = ship.position
        val plasticGarbage = currentTile.garbage.filter { it.type == GarbageType.PLASTIC }.sortedBy { it.id }
        val oilGarbage = currentTile.garbage.filter { it.type == GarbageType.OIL }.sortedBy { it.id }
        val chemicalsGarbage = currentTile.garbage.filter { it.type == GarbageType.CHEMICALS }.sortedBy { it.id }

        if (garbageTypes().contains(GarbageType.PLASTIC) && plasticGarbage.isNotEmpty()) {
            collectPlasticGarbage(ship, plasticGarbage, shipsOnTheTile)
        }

        for (oil in oilGarbage) {
            val collected = collectGarbage(oil.amount, GarbageType.OIL)
            oil.amount -= collected
            if (oil.amount == 0) {
                ship.position.garbage.remove(oil)
                oil.trackedBy
                    .forEach { corp ->
                        corp.trackedGarbage.remove(oil)
                    }
            }
            if (collected > 0) {
                LoggerCorporationAction.logGarbageCollectionByShip(ship, GarbageType.OIL, oil.id, collected)
            }
        }

        for (chem in chemicalsGarbage) {
            val collected = collectGarbage(chem.amount, GarbageType.CHEMICALS)
            chem.amount -= collected
            if (chem.amount == 0) {
                ship.position.garbage.remove(chem)
                chem.trackedBy
                    .forEach { corp ->
                        corp.trackedGarbage.remove(chem)
                    }
            }
            if (collected > 0) {
                LoggerCorporationAction.logGarbageCollectionByShip(ship, GarbageType.CHEMICALS, chem.id, collected)
            }
        }

        auxiliaryContainers.forEach {
            if (it.garbageLoad == it.getGarbageCapacity()) {
                unloading = true
            }
        }
    }

    private fun collectPlasticGarbage(
        ship: Ship,
        plastic: List<Garbage>,
        shipsOnTheTile: List<Ship>
    ) {
        val sumOfPlastic = plastic.sumOf { it.amount }
        val capability: MutableList<CollectingShip> = mutableListOf()

        shipsOnTheTile.plus(ship).forEach { other ->
            val cap = other.capabilities.filterIsInstance<CollectingShip>()
            if (cap.isNotEmpty()) {
                capability.addAll(cap)
            }
        }

        // This needs fixed: we can collect only one garbage patch at the time.
        val sumOfContainers = capability.sumOf { it.hasPlasticCapacity() }
        if (sumOfContainers < sumOfPlastic) {
            return
        }
        for (garbage in plastic) {
            val collected = collectGarbage(garbage.amount, garbage.type)
            garbage.amount -= collected
            if (garbage.amount == 0) {
                ship.position.garbage.remove(garbage)
                garbage.trackedBy
                    .forEach { corp ->
                        corp.trackedGarbage.remove(garbage)
                    }
            }
            if (collected > 0) {

                LoggerCorporationAction.logGarbageCollectionByShip(ship, GarbageType.PLASTIC, garbage.id, collected)
            }
        }
        if (this.auxiliaryContainers.any { it.garbageLoad == it.getGarbageCapacity() }) {
            ship.isInWayToRefuelOrUnload = true
            val pathToHarbor = Helper().findClosestHarbor(ship.position, ship.owner.ownedHarbors)
            ship.destinationPath = pathToHarbor
        }
    }

    /**
     * Takes garbage Types from containers
     * This is wrong
     */
    fun garbageTypes(): Set<GarbageType> {
        return auxiliaryContainers.map { it.garbageType }.toSet()
    }

    private fun collectGarbage(amount: Int, garbageType: GarbageType): Int {
        var collected = 0
        val containers = auxiliaryContainers.filter { it.garbageType == garbageType }
        for (container in containers) {
            val collect: Int = container.collect(amount, garbageType)
            collected += collect
        }
        return collected
    }

    /** Check capacity for specific type*/
    fun capacityForType(type: GarbageType): Int {
        return auxiliaryContainers.filter { it.garbageType == type }.sumOf { it.getGarbageCapacity() - it.garbageLoad }
    }
}
