package de.unisaarland.cs.se.selab.tiles

class Tile {
    private var id: Int = 0
    private var pos: Vec2D = Vec2D(0, 0)
    internal var neighbours: Array<Tile?> = Array(6) { null }

    /**
     * The amount of restrictions acting on this tile. If > 0, then tile is not traversable.
     */
    private var restrictions: Int = 0
}
