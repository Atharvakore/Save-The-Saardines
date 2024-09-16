package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Garbage

class CollectingShip(
    private var garbageType: Set<GarbageType>,
    private var garbageCapacity: Int,
    private var garbageLoad: Int,
    private var auxiliaryContainers: Container
): ShipCapability() {

    /**
     * Call: when a collecting ship is on the harbor, and it has to unload
     * Logic: gets rid of the garbage load
     */
    /**
     * TODO: Implement.
     */
    fun giveGarbage(): Unit{
        TODO("")
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
    fun hasGarbageCapacity(garbage: List<Garbage>): Boolean {
        TODO("")
    }

    /**
     * Call: when the corporation is calling on its ships to collect the garbage
     * Logic: checks the garbage of its tile, collects it if it can
     */
    /**
     * TODO: Implement.
     */
    fun collectGarbageFromCurrentTile(): Unit{
        TODO("")
    }
}