package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.logger.LoggerEventsAndTasks
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Tile

/** Task for finding garbage. */
class FindGarbageTask(
    tick: Int,
    id: Int,
    taskShip: Ship,
    reward: Reward,
    rewardShip: Ship,
    private val targetTile: Tile
) : Task(tick, id, taskShip, reward, rewardShip) {
    override fun toString(): String {
        return "FIND"
    }

    override fun checkCondition(): Boolean {
        return taskShip.position.pos == targetTile.pos && targetTile.garbage.isNotEmpty()
    }

    override fun actUponTick(currentTick: Int): Boolean {
        if (currentTick == tick) {
            LoggerEventsAndTasks.logTaskStart(id, "FIND", taskShip.id, targetTile.id)
        }
        if (checkCondition()) {
            LoggerEventsAndTasks.logRewardReceived(id, rewardShip.id, reward)
            reward.applyReward(rewardShip)
            return true
        }
        return false
    }

    override fun getGoal(): Tile {
        return targetTile
    }
}
