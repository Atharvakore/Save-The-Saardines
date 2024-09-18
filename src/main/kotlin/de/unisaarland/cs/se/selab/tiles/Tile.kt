package de.unisaarland.cs.se.selab.tiles

/**
 *class tile includes all basic functionality related tiles
 */
abstract class Tile(
    val id: Int,
    val pos: Vec2D,
    var adjacentTiles: List<Tile?>,
    var garbage: List<Garbage>,
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
<<<<<<< Updated upstream
        require(id >= 0) { "Id Should be greater than or equal 0" }
=======

        require(id > 0) { "Id Should be greater than 0" }

>>>>>>> Stashed changes
    }

    /**
     * Get tiles in specified Direction
     */
    public fun getTileInDirection(
        distance: Int,
        dir: Direction,
    ): Tile? {
        var tile: Tile? = this
        var i = 0
        while (i <= distance) {
            if (tile?.adjacentTiles?.get(dir.ordinal) == null) {
                return tile
            }
            tile = tile.adjacentTiles[dir.ordinal]!!
            i++
        }
        return tile
    }

    /**
     *
     * adds given Garbage to the List of already present Garbage
     */
    public fun addGarbage(garbage: Garbage) {
        this.garbage += garbage
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
        var filteredList =
            this.garbage
                .filter { it.type == type }
                .sortedBy(Garbage::id)
        while (toBeRemoved > 0 && filteredList.isNotEmpty()) {
            if (toBeRemoved >= filteredList[0].amount) {
                toBeRemoved -= filteredList[0].amount
                filteredList = filteredList.filterIndexed { index, _ -> index != 0 } // removes element at 0th Index
            }
            if (toBeRemoved < filteredList[0].amount) {
                filteredList[0].amount -= toBeRemoved
                toBeRemoved = 0
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
