package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.Ship

abstract class Reward(val id: Int) {
    abstract fun applyReward(ship: Ship)
}