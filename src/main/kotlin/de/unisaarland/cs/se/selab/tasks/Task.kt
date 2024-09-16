package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.Ship

abstract class Task(
    val tick: Int,
    val id: Int,
    val taskShip: Ship,
    val reward: Reward,
    val rewardShip: Ship,
    val corporation: Corporation
) {
    abstract fun checkCondition(): Boolean
    abstract fun actUponTick(currentTick: Int): Boolean
}