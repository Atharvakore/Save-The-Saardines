package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Tile

class FindGarbageTask(
    tick: Int,
    id: Int,
    taskShip: Ship,
    reward: Reward,
    rewardShip: Ship,
    corporation: Corporation,
    private val targetTile: Tile
): Task(tick, id, taskShip, reward, rewardShip, corporation) {
    override fun toString(): String {
        return "Find Garbage"
    }
    override fun checkCondition(): Boolean {
        return taskShip.getPosition().pos == targetTile.pos && targetTile.garbage.isNotEmpty()
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