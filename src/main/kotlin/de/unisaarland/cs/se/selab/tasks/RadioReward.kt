package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.Ship

class RadioReward(id: Int, private val capability: CoordinatingShip) : Reward(id) {
    override fun applyReward(ship: Ship) {
        if (!ship.capabilities.any { it is CoordinatingShip }) {
            ship.capabilities.add(capability)
        }
    }
}