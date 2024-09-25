package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.logger.LoggerEventsAndTasks
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.MaxGarbageId.createGarbage
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile
import kotlin.math.min

/** Oil Spill Event */
class OilSpill(
    override val id: Int,
    override val fireTick: Int,
    override val map: Sea,
    override val location: Tile,
    override val radius: Int,
    private val amount: Int
) : LocalEvent(id, fireTick, map, location, radius) {
    override fun toString(): String {
        return "OIL_SPILL"
    }

    /** Constants in the oil spill event */
    companion object {
        private const val OIL_TILE_MAX = 1000
    }

    override fun actUponTick(currentTick: Int, corporations: List<Corporation>): Boolean {
        if (currentTick == fireTick) {
            // Each tile can hold 1000 units of oil
            val affectedTiles: MutableList<Tile?> = mutableListOf()
            location.pos.tilesInRadius(radius).forEach {
                val tile = map.getTileByPos(it)
                affectedTiles.add(tile)
            }

            affectedTiles
                .sortedBy { it?.id }
                .forEach {
                    val tile = it ?: return@forEach
                    val garbageTiles = tile.garbage.filter { garbage -> garbage.type == GarbageType.OIL }
                    val oilGarbageAmount = OIL_TILE_MAX - garbageTiles.sumOf { garbage -> garbage.amount }
                    val newGarbageAmount = min(amount, oilGarbageAmount)
                    val newGarbage = createGarbage(newGarbageAmount, GarbageType.OIL)
                    tile.garbage = tile.garbage.plus(newGarbage)

                    corporations.forEach { corporation ->
                        corporation.partnerGarbage.putIfAbsent(newGarbage.id, tile)
                    }
                }

            LoggerEventsAndTasks.logEventStart(id, this)
            return true
        }
        return false
    }
}
