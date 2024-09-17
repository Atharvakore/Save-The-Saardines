package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.ships.ShipWithTracker

/** Reward that adds a tracker. */
class TrackerReward(id: Int, private val capability: ShipWithTracker) : Reward(id) {
    override fun toString(): String {
        return "Tracker"
    }

    override fun applyReward(ship: Ship) {
        if (!ship.capabilities.any { it is ShipWithTracker }) {
            ship.capabilities.add(capability)
        }
    }
}
