package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.logger.LoggerEventsAndTasks
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
        return "COLLECT"
    }

    override fun checkCondition(): Boolean {
        return taskShip.position.pos == targetTile.pos
    }

    override fun actUponTick(currentTick: Int): Boolean {
        if (currentTick == tick) {
            LoggerEventsAndTasks.logTaskStart(id, "COLLECT", taskShip.id, targetTile.id)
        }
        if (checkCondition() && currentTick > tick) {
            taskShip.currentVelocity = 0
            LoggerEventsAndTasks.logRewardReceived(id, rewardShip.id, reward)
            reward.applyReward(rewardShip)
            taskShip.owner.tasks.remove(this)
            return true
        }
        return false
    }

    override fun getGoal(): Tile {
        return targetTile
    }
}
