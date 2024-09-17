package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.tiles.Tile

/** Data class that stores information about a ship (of any capability. */
data class ShipInformation(val id: Int, val owner: Corporation, val name: String, var pos: Tile)
