package de.unisaarland.cs.se.selab.corporation

import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile

class Helper {
    /**
     * Filter collecting ships
     *
     * Filters the owned ships to get only the ships that have the CollectingShip capability
     *
     * @return List of collecting ships
     */
    fun filterCollectingShip(corporation: Corporation): List<Ship> {
        return corporation.ownedShips.filter { ownedShip -> ownedShip.capabilities.any { it is CollectingShip } }
    }

    /**
     * Filter scouting ships
     *
     * Filters the owned ships to get only the ships that have the ScoutingShip capability
     * make it private later
     *
     * @return List of scouting ships
     */
    fun filterScoutingShips(corporation: Corporation): List<Ship> {
        return corporation.ownedShips.filter { ownedShip -> ownedShip.capabilities.any { it is ScoutingShip } }
    }

    /**
     * Filter coordinating ships
     *
     * Filters the owned ships to get only the ships that have the CoordinatingShip capability
     *
     * @return List of coordinating ships
     */
    fun filterCoordinatingShips(corporation: Corporation): List<Ship> {
        return corporation.ownedShips.filter { ownedShip -> ownedShip.capabilities.any { it is CoordinatingShip } }
    }

    /**
     * Gets all the ships on the harbor
     *
     * Filters the owned ships to get only the ships that are on a harbor tile
     *
     * @return List of ships on the harbor
     */
    fun getShipsOnHarbor(corporation: Corporation): List<Ship> {
        val seaInstance: Sea = Sea
        val harborTiles: List<Tile> = seaInstance.tiles.filter { (it as Shore).harbor }
        return corporation.ownedShips.filter { harborTiles.contains(it.position) }
    }
}