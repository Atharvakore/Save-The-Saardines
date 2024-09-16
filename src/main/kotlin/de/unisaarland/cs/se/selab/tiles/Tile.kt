package de.unisaarland.cs.se.selab.tiles
import kotlin.math.floor
import kotlin.math.sqrt

const val HUNDRED = 100
const val TWO = 2
const val THREE = 3.0
const val SIX = 6

/**
 *class tile includes all basic functionality related tiles
 */
abstract class Tile(
    val id: Int,
    val pos: Vec2D,
    val adjacentTiles: List<Tile>,
    var garbages: List<Garbage>,
    var amountOfGarbageDriftedThisTick: Int,
) {
    /* private var id: Int = 0
    private var pos: Vec2D = Vec2D(0, 0)
    internal var neighbours: Array<Tile?> = Array(Direction.D300.ordinal) { null } */

    /**
     * The amount of restrictions acting on this tile. If > 0, then tile is not traversable.
     */

    var shipTransversable: Boolean = true

    init {

        require(id > 0) { "Id Should be greater than 0" }
        require(adjacentTiles.size == SIX) { "A tile has 6 neighbours" }
    }

    /**
     * checks for capacity of garbage type in a particular tile
     */
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
        return a + amount < HUNDRED
    }

    /**
     * gives a tile In direction uses basic trigonometry rules to calculate vec2D
     */
    public fun getTileInDirection(
        distance: Int,
        dir: Direction,
    ): Tile? {
        var temp: Vec2D

        when (dir) {
            Direction.D0 -> temp = Vec2D(this.pos.posX + distance, this.pos.posY)
            Direction.D60 ->
                temp =
                    Vec2D(
                        this.pos.posX + (distance / 2),
                        this.pos.posY - floor(distance * (sqrt(THREE) / TWO)).toInt()
                    )

            Direction.D120 ->
                temp =
                    Vec2D(
                        this.pos.posX - (distance / 2),
                        this.pos.posY - floor(distance * (sqrt(THREE) / TWO)).toInt()
                    )

            Direction.D180 -> temp = Vec2D(this.pos.posX - distance, this.pos.posY)
            Direction.D240 ->
                temp =
                    Vec2D(
                        this.pos.posX - (distance / 2),
                        this.pos.posY + floor(distance * (sqrt(THREE) / TWO)).toInt()
                    )

            Direction.D300 ->
                temp =
                    Vec2D(
                        this.pos.posX + (distance / 2),
                        this.pos.posY + floor(distance * (sqrt(THREE) / TWO)).toInt()
                    )
        }
        return Sea.getTileByPos(temp)
    }

    /**
     *
     * adds given Garbage to the List of already present Garbage
     */
    public fun addGarbage(garbage: Garbage) {
        garbages += garbage
    }

    /**
     * Takes a garbnage type and returns total ammount of garbage of that type
     */
    public fun getAmountOfType(type: Garbage): Int {
        var acc = 0
        for (garbage in garbages) {
            if (type.type == garbage.type) {
                acc += garbage.amount
            }
        }
        return acc
    }

    /**
     * removes garbage of particular type
     */
    public fun removeGarbageOfType(
        type: GarbageType,
        ammount: Int,
    ) {
        var toBeRemoved = ammount
        var filteredList =
            this.garbages
                .filter { it.type == type }
                .sortedBy(Garbage::id)
        while (toBeRemoved > 0 && filteredList.isNotEmpty()) {
            if (toBeRemoved >= filteredList[0].amount) {
                toBeRemoved -= filteredList[0].amount
                filteredList = filteredList.filterIndexed { index, _ -> index != 0 } //removes element at 0th Index
            }
            if (toBeRemoved < filteredList[0].amount) {
                filteredList[0].amount -= toBeRemoved
                toBeRemoved = 0
                break
            }
        }
    }

    /**
     * Calculates amount which can be drifted  in a single drift in one tick
     */
    public fun amountTOBeDrifted() {
        // TOdo
    }
}
