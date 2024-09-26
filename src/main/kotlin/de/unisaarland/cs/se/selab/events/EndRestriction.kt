package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile

/** Event which will end the Restriction Event*/
class EndRestriction(
    override val id: Int,
    override val fireTick: Int,
    override val map: Sea,
    override val location: Tile,
    override val radius: Int
) : LocalEvent(id, fireTick, map, location, radius) {
    override fun actUponTick(currentTick: Int, corporations: List<Corporation>): Boolean {
        if (currentTick == fireTick) {
            location.pos.tilesInRadius(radius).forEach {
                val tile = map.getTileByPos(it)
                if (tile != null) {
                    tile.restrictions -= 1
                }
            }
            return true
        }
        return false
    }
}
