package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile

/** An abstract class for an event that is local to some area on the map */
abstract class LocalEvent(
    private val id: Int,
    private val fireTick: Int,
    val map: Sea?,
    val location: Tile?,
    val radius: Int
) : Event(id, fireTick)
