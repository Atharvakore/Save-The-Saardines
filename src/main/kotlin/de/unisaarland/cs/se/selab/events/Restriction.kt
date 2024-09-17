package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile

class Restriction(
    private val id: Int,
    private val fireTick: Int,
    private val map: Sea?,
    private val location: Tile,
    private val radius: Int
) : LocalEvent(id, fireTick, map, location, radius) {
    override fun toString(): String {
        return "Restriction"
    }
    override fun actUponTick(currentTick: Int): Boolean {
        if (currentTick == fireTick) {
            location.pos.tilesInRadius(radius).forEach {
                val tile = map?.getTileByPos(it)
                tile?.restrictions?.inc()
            }
            return true
        }
        return false
    }
}
