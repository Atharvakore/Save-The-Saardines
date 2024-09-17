package de.unisaarland.cs.se.selab.tiles

/**
 * Sea as static class
 */
object Sea {
    var tiles: MutableList<Tile> = mutableListOf()
    var tileIndex: Map<Vec2D, Tile> = mapOf()

    /**
     * gives tile with the ID given as argument
     */

    fun getTileById(tileId: Int): Tile? {
        for (tile in tiles) {
            if (tile.id == tileId) return tile
        }
        return null
    }

    /**
     * gives tile with the position given as argument
     */
    fun getTileByPos(position: Vec2D): Tile? {
        for (tile in tiles) {
            if (tile.pos == position) return tile
        }
        return null
    }

}
