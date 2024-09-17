package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Tile

class ExploreMapTask(
    tick: Int,
    id: Int,
    taskShip: Ship,
    reward: Reward,
    rewardShip: Ship,
    corporation: Corporation,
    private val targetTile: Tile
): Task(tick, id, taskShip, reward, rewardShip, corporation) {
    override fun toString(): String {
        return "Explore Map"
    }
    override fun checkCondition(): Boolean {
        return taskShip.getPosition().pos == targetTile.pos
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