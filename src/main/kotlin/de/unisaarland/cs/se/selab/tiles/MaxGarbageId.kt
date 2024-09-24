package de.unisaarland.cs.se.selab.tiles

/** Object which keep track of maxID of Garbage*/
object MaxGarbageId {
    var maxId: Int = 0

    private fun getNextId(): Int {
        maxId++
        return maxId
    }

    /**
     * Creates garbage when needed
     */
    fun createGarbage(
        amount: Int,
        type: GarbageType,
    ): Garbage = Garbage(getNextId(), amount, type, null)
}
