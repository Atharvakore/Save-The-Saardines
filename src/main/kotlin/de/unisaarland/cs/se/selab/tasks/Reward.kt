package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.Ship

/*
Super-abstract class for rewards
 */
abstract class Reward(open val id: Int) {
    abstract fun applyReward(ship: Ship)
}
