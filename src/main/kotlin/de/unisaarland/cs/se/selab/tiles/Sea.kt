package de.unisaarland.cs.se.selab.tiles

/**
 * Sea as static class
 */
public object Sea {
    val tiles: List<Tile> = listOf()
    val tileIndex: Map<Vec2D, Tile> = mapOf()

    /**
     * gives tile with the ID given as argument
     */

    public fun getTileById(tileId: Int): Tile? {
        for (tile in tiles) {
            if (tile.id == tileId) return tile
        }
        return null
    }
    /**
     * gives tile with the position given as argument
     */
    public fun getTileByPos(position: Vec2D): Tile? {
        for (tile in tiles) {
            if (tile.pos == position) return tile
        }
        return null
    }
}
