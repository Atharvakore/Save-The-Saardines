package de.unisaarland.cs.se.selab.ships

class ScoutingShip (private val visibilityRange: Int): ShipCapability() {
    /**
     * Call: from corporation run method to gather information
     * Logic: the ship checks all tiles in its vibility range for garbage and returns them
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