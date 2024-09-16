package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.Ship

/*
Superclass for tasks
 */
abstract class Task(
    open val tick: Int,
    open val id: Int,
    open val taskShip: Ship,
    open val reward: Reward,
    open val rewardShip: Ship,
    open val corporation: Corporation
) {
    abstract fun checkCondition(): Boolean
    abstract fun actUponTick(currentTick: Int): Boolean
}
