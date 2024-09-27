package de.unisaarland.cs.se.selab.ships

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
     * return if container is full
     */
    fun shouldUnload(): Boolean {
        return garbageLoad == garbageCapacity
    }

    /**
     * return max garbage capacity
     * */
    fun getGarbageCapacity(): Int {
        return this.garbageCapacity
    }

    /**
     * unload garbage
     */
    fun giveGarbage() {
        this.garbageLoad = 0
    }

    /**
     * collect the garbage
     */
    fun collect(amount: Int, type: GarbageType): Int {
        var collected = 0
        if (type == this.garbageType) {
            collected = updateGarbageLoad(amount)
        }
        if (garbageLoad == garbageCapacity) {
            return collected
        }
        return collected
    }

    /**
     * helper fun for collect garbage, return the amount it collected
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
