package de.unisaarland.cs.se.selab.ships

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.tiles.Tile

data class ShipInformation(val id: Int, val owner: Corporation, val name : String, var pos: Tile)
