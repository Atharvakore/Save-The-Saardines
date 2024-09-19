package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Tile

/**
 * class representing the collecting ship capability
 */
class CollectingShip(
    var auxiliaryContainers: MutableList<Container>
) : ShipCapability {
    /**
     * unloads all the containers of the ship
     */
    fun unload() {
        for (container in auxiliaryContainers) {
            container.giveGarbage()
        }
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
    fun collectGarbageFromCurrentTile(currentTile: Tile) {
        val acceptableGarbageType = garbageTypes()
        for (garbageType in acceptableGarbageType) {
            val amount = currentTile.getAmountOfType(garbageType)
            val collected = collectGarbage(amount, garbageType)
            currentTile.removeGarbageOfType(garbageType, collected)
        }
    }

    fun garbageTypes(): Set<GarbageType> {
        return auxiliaryContainers.map { it.garbageType }.toSet()
    }

    private fun collectGarbage(amount: Int, garbageType: GarbageType): Int {
        var collected = 0
        for (container in auxiliaryContainers) {
            collected += container.collect(amount, garbageType)
        }
        return collected
    }
}
