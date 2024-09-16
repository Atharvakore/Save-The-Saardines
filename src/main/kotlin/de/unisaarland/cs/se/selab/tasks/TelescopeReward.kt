package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship

/*
Class for Rewards of type Telescope
 */
class TelescopeReward(override val id: Int, val additiveFieldOfView: Int, val capability: ScoutingShip): Reward(id) {
    override fun applyReward(ship: Ship) {
        TODO("Not yet implemented")
    }
}
