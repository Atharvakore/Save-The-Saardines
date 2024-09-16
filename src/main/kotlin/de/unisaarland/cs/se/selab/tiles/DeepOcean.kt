package de.unisaarland.cs.se.selab.tiles

class DeepOcean(
    id: Int,
    pos: Vec2D,
    adjacentTiles: Array<Tile?>,
    garbage: List<Garbage>,
    private var current: Current?,
) : Tile(
        id,
        pos,
        adjacentTiles,
        garbage,
) {
    public fun getCurrent(): Current? = current
}
