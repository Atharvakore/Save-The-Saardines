package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.Ship

class RadioReward(
    override val id: Int,
    val capability: CoordinatingShip): Reward(id) {
    override fun applyReward(ship: Ship) {
        TODO("Not yet implemented")
    }

}
