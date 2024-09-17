package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.Ship

/** Reward that adds a radio. */
class RadioReward(id: Int, private val capability: CoordinatingShip) : Reward(id) {
    override fun toString(): String {
        return "Radio"
    }

    override fun applyReward(ship: Ship) {
        if (!ship.capabilities.any { it is CoordinatingShip }) {
            ship.capabilities.add(capability)
        }
    }
}
