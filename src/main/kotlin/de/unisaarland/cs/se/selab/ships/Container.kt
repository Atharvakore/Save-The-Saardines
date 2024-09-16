package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
class Container(
    val garbageType: GarbageType,
    private val garbageCapacity: Int,
    var garbageLoad: Int,  // change to the design
)
{
    private fun updateGarbageLoad(amount: Int): Int {
        val collectableAmount = garbageCapacity - garbageLoad
        if (collectableAmount >= amount) {
            garbageLoad += amount
            return amount
        } else {
            garbageLoad += collectableAmount
            return collectableAmount
        }
    }

    /**
     * Call: when a collecting ship is on the harbor, and it has to unload
     * Logic: gets rid of the garbage load
     */
    /**
     * TODO: Implement.
     */
    fun giveGarbage(): Unit{
        this.garbageLoad = 0
    }

    fun collect(garbage: Garbage): Boolean {
        if (garbage.type == garbageType){
            val toBeCollected = garbage.amount
            val collected = updateGarbageLoad(toBeCollected)
            garbage.removeAmount(collected)
            return true
        } else {
            return false
        }
    }
}