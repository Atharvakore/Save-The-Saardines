package de.unisaarland.cs.se.selab.tiles

class Shore(
    id: Int,
    pos: Vec2D,
    adjacentTiles: Array<Tile?>,
    garbage: List<Garbage>
    harbor:Boolean
) : Tile(
    id,
    pos,
    adjacentTiles,
    garbage,
) {
    public fun hasHarbor() = Boolean
}
