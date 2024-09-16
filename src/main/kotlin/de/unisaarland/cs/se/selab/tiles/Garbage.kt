package de.unisaarland.cs.se.selab.tiles
import de.unisaarland.cs.se.selab.corporation .Corporation

/**
 * Garbage class implementing all minor stuff related to Garbage
 */
class Garbage(
    val id: Int,
    var amount: Int,
    val type: GarbageType,
    private var trackedBy: Set<Corporation>?,
) {
    companion object {
        var maxId: Int = 0

        private fun getNextId(): Int = maxId++

        /**
         * Creates garbage when needed
         */
        fun createGarbage(
            amount: Int,
            type: GarbageType,
        ): Garbage = Garbage(getNextId(), amount, type, null)
    }

    /**
     * drifts possible garbage to the respected tile
     */
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
