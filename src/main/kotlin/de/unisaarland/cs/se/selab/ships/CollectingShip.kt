package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.Tile
class CollectingShip (
    var auxiliaryContainers: MutableList<Container>
): ShipCapability {

    public fun unload(): Unit{
        for (container in auxiliaryContainers){
            container.giveGarbage()
        }
    }
    /**
     * Call: when a collecting ship is being checked if it can collect the garbage
     * Logic: it checks if this ship can collect the garbage based on the garabge type
     * If it is oil then it returns true if it can collect any amount, if plastic it returns true if it can
     * collect the whole amount of plastic
     *
     * @param  a list of garbage
     * @return true/false depending if the ship can collect the garbage based on its type
     * */
    /**
     * TODO: Implement.
     */
    public fun hasGarbageCapacity(garbage: List<Garbage>): Boolean {
        TODO("")
    }
    /**
     * Call: when the corporation is calling on its ships to collect the garbage
     * Logic: checks the garbage of its tile, collects it if it can
     */
    /**
     * TODO: Implement.
     */
    public fun collectGarbageFromCurrentTile(currentTile: Tile?): Unit {
        val garbageList = currentTile?.garbage
        val acceptableGarbageType = garbageTypes()
        if (garbageList != null) {
            for (garbage in garbageList) {
                if (garbage.type in acceptableGarbageType) {
                    collectGarbage(garbage)
                }
            }
        }
    }
    private fun garbageTypes() : Set<GarbageType> {
        return auxiliaryContainers.map { it.garbageType }.toSet()
    }
    private fun collectGarbage(garbage: Garbage): Boolean {
        return auxiliaryContainers.any { it.collect(garbage) }
    }
}