package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile
import kotlin.math.min

class OilSpill(
    private val id: Int,
    private val fireTick: Int,
    private val map: Sea?,
    private val location: Tile,
    private val radius: Int,
    private val amount: Int
) : LocalEvent(id, fireTick, map, location, radius) {
    companion object {
        private const val OIL_TILE_MAX = 1000
    }
    override fun actUponTick(currentTick: Int): Boolean {
        if (currentTick == fireTick) {
            // Each tile can hold 1000 units of oil
            location.pos.tilesInRadius(radius).forEach {
                val tile = map.getTileByPos(it) ?: return@forEach
                val garbageTiles = tile.garbages.filter { it.type == GarbageType.OIL }
                val oilGarbageAmount = OIL_TILE_MAX - garbageTiles.sumOf { it.amount }
                val newGarbageAmount = min(amount, oilGarbageAmount)
                tile.garbages = tile.garbages.plus(Garbage.createGarbage(newGarbageAmount, GarbageType.OIL))
            }
            return true
        }
        return false
    }
}
