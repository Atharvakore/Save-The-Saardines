package de.unisaarland.cs.se.selab.tiles

/**
 * Shore class
 */

class Shore(
    id: Int,
    pos: Vec2D,
    adjacentTiles: List<Tile>,
    garbage: List<Garbage>,
    val harbor:Boolean
) : Tile(
    id,
    pos,
    adjacentTiles,
    garbage,0
)
