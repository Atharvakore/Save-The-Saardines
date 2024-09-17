package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.Ship

/** Abstract class for rewards */
abstract class Reward(open val id: Int) {
    /** Apply the rewards to ships */
    abstract fun applyReward(ship: Ship)
}
