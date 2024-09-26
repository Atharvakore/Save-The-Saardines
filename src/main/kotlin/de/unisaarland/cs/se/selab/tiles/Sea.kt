package de.unisaarland.cs.se.selab.tiles

/**
 * Sea as static class
 */
class Sea {
    // Do not change this val to var: this causes a Detekt error.
    val tiles: MutableList<Tile> = mutableListOf()
    var tileIndex: Map<Vec2D, Tile> = emptyMap()
    var garbageOnMap = 0

    /**
     * gives tile with the ID given as argument
     */

    fun getTileById(tileId: Int): Tile? {
        for (tile in tiles) {
            if (tile.id == tileId) {
                return tile
            }
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

    /** Calculate the amount of garbage still in ocean; Needed only for Statistics*/
    fun calculateGarbageOnMap(): Int {
        var result = 0
        for (tile in tiles) {
            result += tile.garbage.sumOf { it.amount }
        }
        return result
    }
}
