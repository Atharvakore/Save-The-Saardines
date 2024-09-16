package de.unisaarland.cs.se.selab.tiles

/**
 * An enumeration of the six possible directions in which
 * a tile can have a neighbour.
 */
enum class Direction(private val dir: Int) {
    D0(0),
    D60(60),
    D120(120),
    D180(180),
    D240(240),
    D300(300);

    companion object {
        fun getDirection(dir: Int): Direction? {
            return entries.find { it.dir == dir }
        }
    }
}
