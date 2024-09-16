package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.ships.ShipWithTracker

class TrackerReward(id: Int, private val capability: ShipWithTracker) : Reward(id) {
    override fun applyReward(ship: Ship) {
        if (!ship.capabilities.any { it is ShipWithTracker }) {
            ship.capabilities.add(capability)
        }
    }
}