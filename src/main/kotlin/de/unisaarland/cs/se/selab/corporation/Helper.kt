package de.unisaarland.cs.se.selab.corporation

import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Dijkstra
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile

/** Helper class*/
class Helper {
    /**
     * Filter collecting ships
     *
     * Filters the owned ships to get only the ships that have the CollectingShip capability
     *
     * @return List of collecting ships
     */
    fun filterCollectingShip(corporation: Corporation): List<Ship> {
        return corporation.ownedShips.filter { it.capabilities.first() is CollectingShip }
    }

    /**
     * Filter collecting ships
     *
     * Filters the owned ships to get only the ships that have the CollectingShip capability
     *
     * @return List of collecting ships
     */
    fun filterCollectingCapabilities(corporation: Corporation): List<Ship> {
        return corporation.ownedShips.filter { it.capabilities.any { capability -> capability is CollectingShip } }
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
        val ownedHarbors = corporation.ownedHarbors.sortedBy { it.id }
        val ships = corporation.ownedShips.filter { ownedHarbors.contains(it.position) && !it.arrivedToHarborThisTick }
        return ships.sortedBy { it.id }
    }

    /**
     * Find the closest ship to a tile
     *
     * Takes destination tile and list of ships as input, then uses Dijkstra's algorithm to find the
     * shortest path to the closest ship
     *
     * make it private later
     * @param tile Destination tile
     * @param ships List of ships
     * @return List of tiles representing the shortest path to the closest ship
     */
    fun findClosestShip(tile: Tile, ships: List<Ship>): List<Tile> {
        val dijkstra: Dijkstra = Dijkstra(tile)
        val shortestPaths: Map<Tile, List<Tile>> = dijkstra.allPaths()

        var shortestPathLen: Int = Int.MAX_VALUE
        var shortestPath: List<Tile> = emptyList()

        for (ship in ships) {
            val path: List<Tile>? = shortestPaths[ship.position]
            if (path != null && path.size < shortestPathLen) {
                shortestPathLen = path.size
                shortestPath = path
            }
        }

        return shortestPath
    }

    /**
     * Find the closest harbor to a tile
     *
     * Takes destination tile and list of harbors as input, then uses Dijkstra's algorithm to find the
     * shortest path to the closest harbor
     *
     * @param tile Destination tile
     * @param harbors List of harbors
     * @return List of tiles representing the shortest path to the closest harbor
     */
    fun findClosestHarbor(tile: Tile, harbors: List<Shore>): List<Tile>? {
        val x = Dijkstra(tile).allPaths().toList() // (KS: Simplify)
            .sortedWith(compareBy({ it.second.size }, { it.first.id }))
            .firstOrNull { harbors.contains(it.first) }
        if (x != null) {
            return x.second
        }
        return x
    }
}
