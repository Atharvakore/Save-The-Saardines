package de.unisaarland.cs.se.selab.tiles

/**
 *class tile includes all basic functionality related tiles
 */
open class Tile(
    val id: Int,
    val pos: Vec2D,
    var adjacentTiles: List<Tile?>,
    var garbage: MutableList<Garbage>,
    var amountOfGarbageDriftedThisTick: Int,
) {
    /* private var id: Int = 0
    private var pos: Vec2D = Vec2D(0, 0)
    internal var neighbours: Array<Tile?> = Array(Direction.D300.ordinal) { null } */

    /**
     * The amount of restrictions acting on this tile. If > 0, then tile is not traversable.
     * Atharva, do not remove it. It was here for a reason. The reason was explained by this
     * comment above. Merely 'shipTraversable' doesn't work and we've discussed this in the defense.
     * What if you have two overlapping restrictions and want to lift one? Then you have
     * made restricted tiles traversable.
     */
    var restrictions: Int = 0

    init {
        require(id >= 0) { "Id Should be greater than or equal 0" }
    }

    /**
     * Get tiles in specified Direction
     */
    fun getTileInDirection(
        distance: Int,
        dir: Direction,
    ): Tile? {
        var tile: Tile? = this
        var i = 0
        while (i < distance) {
            if (tile?.adjacentTiles?.get(dir.ordinal) == null) {
                return tile
            }
            tile = requireNotNull(tile.adjacentTiles[dir.ordinal])
            i++
        }
        return tile
    }

    /**
     *
     *
     *
     * adds given Garbage to the List of already present Garbage
     */
    public fun addGarbage(garbage: Garbage) {
        this.garbage.add(garbage)
    }

    /**
     * Takes a garbage type and returns total amount of garbage of that type
     */
    public fun getAmountOfType(type: GarbageType): Int {
        var acc = 0
        for (garbage in garbage) {
            if (type == garbage.type) {
                acc += garbage.amount
            }
        }
        return acc
    }

    /**
     * removes garbage of particular type
     */
    fun removeGarbageOfType(
        type: GarbageType,
        amount: Int,
    ) {
        var toBeRemoved = amount
        val filteredList =
            this.garbage
                .filter { it.type == type }
                .sortedBy(Garbage::id)
                .toMutableList()
        for (garbage in filteredList) {
            toBeRemoved = minOf(0, toBeRemoved - garbage.amount)
            garbage.amount = minOf(garbage.amount - toBeRemoved, 0)
            if (garbage.amount == 0) {
                this.garbage.remove(garbage)
                garbage.trackedBy.forEach { corporation ->
                    corporation.trackedGarbage.remove(garbage)
                }
            } else {
                break
            }
        }
    }

    /**
     * returns current oil level of the Tile
     * Note this should not exceed 1000
     */
    fun currentOilLevel(): Int {
        var accumulator = 0
        for (oil in this.garbage.filter { it.type == GarbageType.OIL }) {
            accumulator += oil.amount
        }
        return accumulator
    }
}
