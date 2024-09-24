package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.logger.LoggerEventsAndTasks
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Tile

/** Task that makes a ship go to a tile. */
class ExploreMapTask(
    tick: Int,
    id: Int,
    taskShip: Ship,
    reward: Reward,
    rewardShip: Ship,
    private val targetTile: Tile
) : Task(tick, id, taskShip, reward, rewardShip) {
    override fun toString(): String {
        return "EXPLORE"
    }
    override fun checkCondition(): Boolean {
        return taskShip.position.pos == targetTile.pos
    }

    override fun actUponTick(currentTick: Int): Boolean {
        if (currentTick == tick) {
            LoggerEventsAndTasks.logTaskStart(id, "EXPLORE", taskShip.id, targetTile.id)
        }
        if (checkCondition() && currentTick >= tick) {
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
