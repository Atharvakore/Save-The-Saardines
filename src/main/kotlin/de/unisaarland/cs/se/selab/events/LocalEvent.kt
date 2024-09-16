package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile

abstract class LocalEvent(
    private val id: Int,
    private val fireTick: Int,
    private val map: Sea?,
    private val location: Tile?,
    private val radius: Int
) : Event(id, fireTick)
