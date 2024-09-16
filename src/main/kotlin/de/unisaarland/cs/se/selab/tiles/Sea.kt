package de.unisaarland.cs.se.selab.tiles

public object Sea {
    val tiles: List<Tile> = listOf()
    val tileIndex: Map<Vec2D, Tile> = mapOf()

    public fun getTileById(tileId: Int): Tile? {
        for (tile in tiles) {
            if (tile.id == tileId) return tile
        }
        return null
    }

    public fun getTileByPos(position: Vec2D): Tile? {
        for (tile in tiles) {
            if (tile.pos == position) return tile
        }
        return null
    }
}
