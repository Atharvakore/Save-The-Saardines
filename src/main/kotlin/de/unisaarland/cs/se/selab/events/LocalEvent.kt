package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile

/** An abstract class for an event that is local to some area on the map */
abstract class LocalEvent(
    override val id: Int,
    override val fireTick: Int,
    open val map: Sea,
    open val location: Tile,
    open val radius: Int
) : Event(id, fireTick)
