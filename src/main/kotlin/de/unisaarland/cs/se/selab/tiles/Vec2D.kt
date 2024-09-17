package de.unisaarland.cs.se.selab.tiles

/**
 * A simple 2D vector class. It is used to represent positions on the sea.
 * @property posX the x-coordinate of the vector
 * @property posY the y-coordinate of the vector
 */
class Vec2D(var posX: Int, var posY: Int) {
    override fun toString(): String {
        return "($posX, $posY)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vec2D) return false

        if (posX != other.posX) return false
        if (posY != other.posY) return false

        return true
    }

    /**
     * Returns an iterator over *all* the Vec2D instances in a given radius around this Vec2D.
     */
    fun tilesInRadius(radius: Int) = iterator {
        for (dx in -radius..radius) {
            for (dy in -radius..radius) {
                if (dx + dy in -radius..radius) {
                    yield(Vec2D(posX + dx, posY + dy))
                }
            }
        }
    }
}
