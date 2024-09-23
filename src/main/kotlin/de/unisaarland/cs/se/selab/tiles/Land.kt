package de.unisaarland.cs.se.selab.tiles

/**
 * Dummy class to differentiate Land tiles while parsing: needed for validation checks
 */
class Land(
    id: Int,
    pos: Vec2D,
    adjacentTiles: List<Tile?>
) : Tile(
    id,
    pos,
    adjacentTiles,
    mutableListOf(),
    0
)
