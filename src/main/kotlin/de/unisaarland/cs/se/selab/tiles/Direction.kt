package de.unisaarland.cs.se.selab.tiles

/**
 * An enumeration of the six possible directions in which
 * a tile can have a neighbour.
 */
enum class Direction(private val dir: Int) {
    D0(DIRECTION_EAST),
    D60(DIRECTION_NORTHEAST),
    D120(DIRECTION_NORTHWEST),
    D180(DIRECTION_WEST),
    D240(DIRECTION_SOUTHWEST),
    D300(DIRECTION_SOUTHEAST);

    companion object {
        fun getDirection(dir: Int): Direction? {
            return entries.find { it.dir == dir }
        }
    }
}
