package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Tile

/** Abstract class regarding tasks. */
abstract class Task(
    open val tick: Int,
    open val id: Int,
    open val taskShip: Ship,
    open val reward: Reward,
    open val rewardShip: Ship,
) {
    /** Determine if the reward condition for a task is fulfilled. */
    abstract fun checkCondition(): Boolean

    /** Tick the task. */
    abstract fun actUponTick(currentTick: Int): Boolean

    /** Determine the goal tile of the task. */
    abstract fun getGoal(): Tile

    /** Called when the task is failed */
    abstract fun fail(): Unit
}
