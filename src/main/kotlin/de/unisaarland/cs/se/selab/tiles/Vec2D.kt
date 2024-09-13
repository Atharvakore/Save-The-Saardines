package de.unisaarland.cs.se.selab.tiles

class Vec2D(private var x: Int, private var y: Int) {
    override fun toString(): String {
        return "($x, $y)"
    }

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