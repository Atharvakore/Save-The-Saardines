package de.unisaarland.cs.se.selab.tiles

/**
 * A simple 2D vector class. It is used to represent positions on the sea.
 * @property x the x-coordinate of the vector
 * @property y the y-coordinate of the vector
 */
class Vec2D(private var x: Int, private var y: Int) {
    override fun toString(): String {
        return "($x, $y)"
    }

    /**
     * Returns an iterator over *all* the Vec2D instances in a given radius around this Vec2D.
     */
    fun tilesInRadius(radius: Int) = iterator {
        for (dx in -radius..radius) {
            for (dy in -radius..radius) {
                if (dx + dy in -radius..radius) {
                    yield(Vec2D(x + dx, y + dy))
                }
            }
        }
    }
}
