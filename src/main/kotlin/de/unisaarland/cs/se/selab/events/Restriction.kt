package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.logger.LoggerEventsAndTasks
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile

/** The restriction event. */
class Restriction(
    override val id: Int,
    override val fireTick: Int,
    override val map: Sea,
    override val location: Tile,
    override val radius: Int
) : LocalEvent(id, fireTick, map, location, radius) {
    override fun toString(): String {
        return "RESTRICTION"
    }

    override fun actUponTick(currentTick: Int, corporations: List<Corporation>): Boolean {
        if (currentTick == fireTick) {
            location.pos.tilesInRadius(radius).forEach {
                val tile = map.getTileByPos(it)
                if (tile != null) {
                    tile.restrictions += 1
                }
            }
            LoggerEventsAndTasks.logEventStart(id, this)
            return true
        }
        return false
    }
}
