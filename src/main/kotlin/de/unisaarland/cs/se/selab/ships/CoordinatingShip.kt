package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Tile


class CoordinatingShip (private val visibilityRange: Int) : ShipCapability {
    private val directions = listOf(
        Direction.D0, Direction.D60, Direction.D120,
        Direction.D180, Direction.D240, Direction.D300
    )
    /**
     * Call: from corporation run method to gather information
     * Logic: the ship checks all tiles in its Visibility range for garbage and returns them
     *
     * @param  shipTile the tile the ship is currently on
     * @return the list of all the tiles within its visibility range
     */
    fun getTilesWithGarbageInFoV(shipTile: Tile): List<Tile> {
        val tilesInFov = directions.map { direction ->
            shipTile.getTileInDirection(visibilityRange, direction)
        }
        return filterTilesWithGarbage(tilesInFov)
    }
    private fun filterTilesWithGarbage(tiles: List<Tile?>): List<Tile> {
        return tiles.filterNotNull()
            .filter { it.garbage.isNotEmpty() }
    }
}