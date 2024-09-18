package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile

/** Storm Event */
class Storm(
    override val id: Int,
    override val fireTick: Int,
    override val map: Sea?,
    override val location: Tile?,
    override val radius: Int,
    val speed: Int,
    val direction: Direction
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
