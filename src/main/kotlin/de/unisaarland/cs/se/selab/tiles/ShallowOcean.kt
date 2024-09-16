package de.unisaarland.cs.se.selab.tiles

/**
 * Shallow Ocean Extended from Tile
 */
class ShallowOcean(
    id: Int,
    pos: Vec2D,
    adjacentTiles: List<Tile>,
    garbage: List<Garbage>
) : Tile(
    id,
    pos,
    adjacentTiles,
    garbage,0
)
