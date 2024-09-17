package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile

/** Storm Event */
class Storm(
    private val id: Int,
    private val fireTick: Int,
    private val map: Sea?,
    private val location: Tile?,
    private val radius: Int,
    private val speed: Int,
    private val direction: Direction
) : LocalEvent(id, fireTick, map, location, radius) {
    override fun toString(): String {
        return "Storm"
    }

    override fun actUponTick(currentTick: Int): Boolean {
        if (currentTick == fireTick) {
            // TODO.
            return true
        }
        return false
    }
}
