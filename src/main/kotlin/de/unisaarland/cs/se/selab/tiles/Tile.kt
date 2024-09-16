package de.unisaarland.cs.se.selab.tiles

class Tile {
    private var id: Int = 0
    private var pos: Vec2D = Vec2D(0, 0)
    internal var neighbours: Array<Tile?> = Array(Direction.D300.ordinal) { null }

    /**
     * The amount of restrictions acting on this tile. If > 0, then tile is not traversable.
     */
    private var restrictions: Int = 0

    /** Getter for Id**/
    public fun getId(): Int {
        return this.id
    }

    /** Getter for Position**/
    public fun getCoordinates(): Vec2D {
        return this.pos
    }
}
