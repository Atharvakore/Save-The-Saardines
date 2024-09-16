package de.unisaarland.cs.se.selab.tiles

class ShallowOcean(
    id: Int,
    pos: Vec2D,
    adjacentTiles: Array<Tile?>,
    garbage: List<Garbage>
) : Tile(
    id,
    pos,
    adjacentTiles,
    garbage,
)
