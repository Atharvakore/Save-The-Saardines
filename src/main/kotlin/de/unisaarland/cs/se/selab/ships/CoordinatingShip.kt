package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile
class CoordinatingShip (private val visibilityRange: Int) : ShipCapability(){
    /**
     * Call: from corporation run method to gather information
     * Logic: the ship checks all tiles in its Visibility range for garbage and returns them
     *
     * @param  sea the whole map with all the tiles
     * @return the list of all the tiles within its visibility range
     */
     /**
     * TODO: Implement.
     */
    fun getTilesWithGarbageInFoV(sea: Sea): List <Tile>{
        TODO("")
    }
}