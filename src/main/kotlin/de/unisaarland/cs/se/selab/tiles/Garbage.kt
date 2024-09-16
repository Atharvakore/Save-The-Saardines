package de.unisaarland.cs.se.selab.tiles
import de.unisaarland.cs.se.selab.corporation .Corporation

class Garbage(
    val id: Int,
    var ammount: Int,
    val type: GarbageType,
    private var trackedBy: Set<Corporation>?,
) {
    companion object {
        var maxId: Int = 0

        fun getNextId(): Int = maxId++
    }

    public fun drift(currentTile: DeepOcean) {
        val localcurrent: Current? = currentTile.getCurrent()
        if (localcurrent?.speed != 0 && localcurrent?.intensity != 0 && localcurrent?.direction != null) {
            var speed = localcurrent.speed
            var intensity = localcurrent.intensity
            var direction = localcurrent.direction
        }
    }

    private fun removeAmount(ammount: Int) {
        this.ammount -= ammount
    }

    private fun createGarbage(
        id: Int,
        amount: Int,
        type: GarbageType,
    ): Garbage = Garbage(getNextId(), amount, type, null)
}
