package de.unisaarland.cs.se.selab.tiles

/**
 * Implements deep ocean Tile
 */
class DeepOcean(
    id: Int,
    pos: Vec2D,
    adjacentTiles: List<Tile?>,
    garbage: List<Garbage>,
    private var current: Current?
) : Tile(
    id,
    pos,
    adjacentTiles,
    garbage,
    0
) {
    var amountOfShipsDriftedThisTick = 0

    /**
     * gives current
     */
    fun getCurrent(): Current? = current
}
