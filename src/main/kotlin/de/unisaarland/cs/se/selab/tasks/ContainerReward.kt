package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.GarbageType

/*
Class for Reward of type Container
 */
class ContainerReward(
    override val id: Int,
    val capacity: Int,
    val typeOfGarbage: GarbageType,
    val capability: CollectingShip) : Reward(id) {
    override fun applyReward(ship: Ship) {
        TODO("Not yet implemented")
    }
}
