package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile

/**
 * class representing the coordinating ship capability
 * */
class CoordinatingShip(private val visibilityRange: Int) : ShipCapability {

    /**
     * Call: from corporation run method to gather information
     * Logic: the ship checks all tiles in its Visibility range and returns them
     *
     * @param sea the whole map
     * @param shipTile the tile the ship is currently on
     * @return the list of all the tiles within its visibility range
     */
    fun getTilesInFoV(sea: Sea, shipTile: Tile): List<Tile> {
        val visibleTiles = shipTile.pos.tilesInRadius(visibilityRange)
        val tilesInFov = emptyList<Tile>()
        for (tiles in visibleTiles) {
            tilesInFov.addLast(sea.getTileByPos(tiles))
        }
        return tilesInFov
    }


    /**
     * Call: from corporation run method to gather information
     * Logic: the ship checks all tiles in its Visibility range for garbage and returns them
     *
     * @param sea the whole map
     * @param shipTile the tile the ship is currently on
     * @return the list of all the tiles with garbage within its visibility range
     */
    fun getTilesWithGarbageInFoV(sea: Sea, shipTile: Tile): List<Tile> {
        val visibleTiles = shipTile.pos.tilesInRadius(visibilityRange)
        val tilesInFov = mutableListOf<Tile?>()
        for (tiles in visibleTiles) {
            tilesInFov.add(sea.getTileByPos(tiles))
        }
        return filterTilesWithGarbage(tilesInFov)
    }

    private fun filterTilesWithGarbage(tiles: List<Tile?>): List<Tile> {
        return tiles.filterNotNull()
            .filter { it.garbage.isNotEmpty() }
    }
}
