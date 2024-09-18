package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Tile

/** Class for Collecting Garbage Task*/
class CollectGarbageTask(
    tick: Int,
    id: Int,
    taskShip: Ship,
    reward: Reward,
    rewardShip: Ship,
    val targetTile: Tile
) : Task(tick, id, taskShip, reward, rewardShip) {
    override fun toString(): String {
        return "Collect Garbage"
    }

    override fun checkCondition(): Boolean {
        return taskShip.position.pos == targetTile.pos
    }

    override fun actUponTick(currentTick: Int): Boolean {
        if (checkCondition()) {
            reward.applyReward(rewardShip)
            return true
        }
        return false
    }

    override fun getGoal(): Tile {
        return targetTile
    }
}
