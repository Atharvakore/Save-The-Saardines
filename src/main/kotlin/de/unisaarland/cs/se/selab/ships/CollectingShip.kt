package de.unisaarland.cs.se.selab.ships

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
    fun collectGarbageFromCurrentTile(ship: Ship) {
        /** val acceptableGarbageType = garbageTypes() **/

        val currentTile: Tile = ship.position
        val plasticGarbage: List<Garbage> = currentTile.garbage.filter { it.type == GarbageType.PLASTIC }
        val oilGarbage: List<Garbage> = currentTile.garbage.filter { it.type == GarbageType.OIL }
        val chemicalsGarbage: List<Garbage> = currentTile.garbage.filter { it.type == GarbageType.CHEMICALS }

        /** for (garbageType in acceptableGarbageType) {
         val amount = currentTile.getAmountOfType(garbageType)
         val collected = collectGarbage(amount, garbageType)
         currentTile.removeGarbageOfType(garbageType, collected)
         } **/

        for (plastic in plasticGarbage) {
            collectGarbage(ship, plastic, plastic.amount, GarbageType.PLASTIC)
            // currentTile.removeGarbageOfType(GarbageType.PLASTIC, collected)
            // LoggerCorporationAction.logGarbageCollectionByShip(ship, GarbageType.PLASTIC, plastic.id, collected)
        }

        for (oil in oilGarbage) {
            collectGarbage(ship, oil, oil.amount, GarbageType.OIL)
            // currentTile.removeGarbageOfType(GarbageType.OIL, collected)
            // LoggerCorporationAction.logGarbageCollectionByShip(ship, GarbageType.OIL, oil.id, collected)
        }

        for (chemicals in chemicalsGarbage) {
            collectGarbage(ship, chemicals, chemicals.amount, GarbageType.CHEMICALS)
            // currentTile.removeGarbageOfType(GarbageType.CHEMICALS, collected)
        }
    }

    /**
     * Takes garbage Types from containers
     * This is wrong
     */
    fun garbageTypes(): Set<GarbageType> {
        return auxiliaryContainers.map { it.garbageType }.toSet()
    }

    private fun collectGarbage(ship: Ship, garbage: Garbage, amount: Int, garbageType: GarbageType): Int {
        var collected = 0
        for (container in auxiliaryContainers) {
            val collect: Int = container.collect(amount, garbageType)
            collected += collect
            if (collect != 0) {
                // GARBAGE IS REMOVED TWICE, ONCE BELOW, AND ANOTHER TIME IN LINE 123
                // ALSO, IN LINE 123, WE ARE GIVING COLLECTED AND NOT COLLECT AS ARGUMENT WHICH IS WRONG
                ship.position.removeGarbageOfType(garbageType, collected)
                LoggerCorporationAction.logGarbageCollectionByShip(ship, container.garbageType, garbage.id, collect)
            }
        }
        return collected
    }

    /** Check capacity for specific type*/
    fun capacityForType(type: GarbageType): Int {
        return auxiliaryContainers.filter { it.garbageType == type }.sumOf { it.getGarbageCapacity() - it.garbageLoad }
    }
}
