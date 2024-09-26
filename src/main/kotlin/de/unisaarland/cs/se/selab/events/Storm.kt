package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.logger.LoggerEventsAndTasks
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile

/** Storm Event */
class Storm(
    override val id: Int,
    override val fireTick: Int,
    override val map: Sea,
    override val location: Tile,
    override val radius: Int,
    val speed: Int,
) : LocalEvent(id, fireTick, map, location, radius) {
    lateinit var direction: Direction

    override fun toString(): String {
        return "STORM"
    }

    override fun actUponTick(currentTick: Int, corporations: List<Corporation>): Boolean {
        val result: Boolean
        if (currentTick == fireTick) {
            LoggerEventsAndTasks.logEventStart(id, this)
            val radiusVec2D = location.pos.tilesInRadius(radius)
            val tiles: MutableList<Tile> = mutableListOf()
            for (pos in radiusVec2D) {
                map.getTileByPos(pos)?.let { tiles.add(it) }
            }
            val tilesWithGarbage = tiles.filter { it.garbage.isNotEmpty() }
            val garbageToAdd: MutableMap<Tile, MutableList<Garbage>> = mutableMapOf()
            val garbageToRemove: MutableMap<Tile, MutableList<Garbage>> = mutableMapOf()
            for (tile in tilesWithGarbage) {
                tile.garbage.forEach { gar ->
                    gar.stormDrift(
                        speed,
                        direction,
                        tile,
                        corporations,
                        garbageToAdd,
                        garbageToRemove
                    )
                }
            }
            garbageToAdd.forEach { (tile, garbageList) -> tile.garbage.addAll(garbageList) }
            garbageToRemove.forEach { (tile, garbageList) -> tile.garbage.removeAll(garbageList) }

            result = true
        } else {
            result = false
        }
        return result
    }
}
