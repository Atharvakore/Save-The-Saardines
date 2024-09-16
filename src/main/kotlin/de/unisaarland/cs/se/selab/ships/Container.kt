package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType

/**
 *  Container that holds info about the garbage: GarbageType, Capacity, current Load
 */
class Container(
    val garbageType: GarbageType,
    private val garbageCapacity: Int,
) {
    var garbageLoad: Int = 0

    /**
     * return max garbage capacity
     * */
    fun getGarbageCapacity(): Int{
        return this.garbageCapacity
    }

    /**
     * unload garbage
     */
    fun giveGarbage(){
        this.garbageLoad = 0
    }

    /**
     * collect the garbage
     */
    fun collect(garbage: Garbage): Boolean {
        if (garbage.type == garbageType) {
            val toBeCollected = garbage.amount
            val collected = updateGarbageLoad(toBeCollected)
            garbage.removeAmount(collected)
            return true
        } else {
            return false
        }
    }

    /**
     * helper fun for collect garbage
     */
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
}