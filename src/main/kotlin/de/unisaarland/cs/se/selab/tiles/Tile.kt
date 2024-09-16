package de.unisaarland.cs.se.selab.tiles
import kotlin.math.floor
import kotlin.math.sqrt

abstract class Tile(
    val id: Int,
    val pos: Vec2D,
    val adjacentTiles: Array<Tile?>,
    var garbages: List<Garbage>,
    var amountOfGarbageDriftedThisTick: Int,
    var amountOfShipsDriftedThisTick: Int
) {
    /* private var id: Int = 0
    private var pos: Vec2D = Vec2D(0, 0)
    internal var neighbours: Array<Tile?> = Array(Direction.D300.ordinal) { null } */

    /**
     * The amount of restrictions acting on this tile. If > 0, then tile is not traversable.
     */
    public var restrictions: Int = 0


    init {
        val six = 6
        require(id > 0) { "Id Should be greater than 0" }
        require(adjacentTiles.size == six) { "A tile has 6 neighbours" }
    }

    public fun isSpaceAvailable(
        type: GarbageType,
        amount: Int,
    ): Boolean {
        var a = 0
        if (type == GarbageType.PLASTIC || type == GarbageType.CHEMICALS) {
            return true
        }

        for (garbage in garbages) {
            if (garbage.type == GarbageType.OIL) {
                a += garbage.amount
            }
        }
        return (a + amount) < 100
    }

    public fun getTileInDirection(
        distance: Int,
        dir: Direction,
    ): Tile? {
        var temp: Vec2D
        val three = 3.0
        when (dir) {
            Direction.D0 -> temp = Vec2D(this.pos.posX + distance, this.pos.posY)
            Direction.D60 ->
                temp =
                    Vec2D(this.pos.posX + (distance / 2), (this.pos.posY - floor(distance * (sqrt(three) / 2)).toInt()))

            Direction.D120 ->
                temp =
                    Vec2D(this.pos.posX - (distance / 2), (this.pos.posY - floor(distance * (sqrt(three) / 2)).toInt()))

            Direction.D180 -> temp = Vec2D(this.pos.posX - (distance), this.pos.posY)
            Direction.D240 ->
                temp =
                    Vec2D(this.pos.posX - (distance / 2), (this.pos.posY + floor(distance * (sqrt(three) / 2)).toInt()))

            Direction.D300 ->
                temp =
                    Vec2D(this.pos.posX + (distance / 2), (this.pos.posY + floor(distance * (sqrt(three) / 2)).toInt()))
        }
        return Sea.getTileByPos(temp)
    }

    public fun addGarbage(garbage: Garbage) {
        garbages += garbage
    }

    public fun getAmountOfType(type: Garbage): Int {
        var acc = 0
        for (garbage in garbages) {
            if (type.type == garbage.type) {
                acc += garbage.amount
            }
        }
        return acc
    }

    public fun removeGarbageOfType(
        type: GarbageType,
        amount: Int,
    ) {
        var toBeRemoved = amount
        var filteredList =
            this.garbages
                .filter { it.type == type }
                .sortedBy(Garbage::amount)

        for (g in filteredList) {
            if (toBeRemoved >= g.amount) {
                toBeRemoved -= g.amount
                filteredList = filteredList.filterIndexed { index, _ -> index != 0 }
            }
            filteredList[0].amount -= toBeRemoved
        }

        // TOdo YET TO BE COMPLETED
    }

    public fun amountTOBeDrifted() {
        // TOdo
    }
    /** Getter for Id**/
    public fun getId(): Int {
        return this.id
    }

    /** Getter for Position**/
    public fun getCoordinates(): Vec2D {
        return this.pos

    }
}
