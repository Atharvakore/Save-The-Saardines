package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.ships.ShipWithTracker

/*
Class for Rewards of type Tracker
 */
class TrackerReward(override val id: Int, val capability: ShipWithTracker) : Reward(id) {
    override fun applyReward(ship: Ship) {
        TODO("Not yet implemented")
    }
}
