package de.unisaarland.cs.se.selab.tiles

import de.unisaarland.cs.se.selab.corporation.Corporation

class Garbage(
    val id: Int,
    var amount: Int,
    val type: GarbageType,
    private var trackedBy: Set<Corporation>?,
) {
    companion object {
        var maxId: Int = 0

        private fun getNextId(): Int = maxId++

        fun createGarbage(
            amount: Int,
            type: GarbageType,
        ): Garbage = Garbage(getNextId(), amount, type, null)
    }

    public fun drift(currentTile: DeepOcean) {
        val localcurrent: Current? = currentTile.getCurrent()
        if (localcurrent?.speed != 0 && localcurrent?.intensity != 0 && localcurrent?.direction != null) {
            var speed = localcurrent.speed
            var intensity = localcurrent.intensity
            var direction = localcurrent.direction
        }
    }

    private fun removeAmount(amount: Int) {
        this.amount -= amount
    }
}
