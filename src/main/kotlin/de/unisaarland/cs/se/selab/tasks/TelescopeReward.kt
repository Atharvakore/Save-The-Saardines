package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship

/** Reward that adds a telescope. */
class TelescopeReward(id: Int, private val capability: ScoutingShip, private val additiveFoV: Int) : Reward(id) {
    override fun toString(): String {
        return "TELESCOPE"
    }

    override fun applyReward(ship: Ship) {
        if (!ship.capabilities.any { it is ScoutingShip }) {
            ship.capabilities.add(capability)
        }
        ship.capabilities.filterIsInstance<ScoutingShip>().forEach {
            it.visibilityRange += additiveFoV
        }
    }
}
