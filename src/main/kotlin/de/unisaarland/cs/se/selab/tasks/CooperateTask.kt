package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Tile

class CooperateTask(
    tick: Int,
    id: Int,
    taskShip: Ship,
    reward: Reward,
    rewardShip: Ship,
    corporation: Corporation,
    private val destinationHomeHarbor: Tile
): Task(tick, id, taskShip, reward, rewardShip, corporation) {
    override fun checkCondition(): Boolean {
        return taskShip.getPos().pos == destinationHomeHarbor.pos
    }

    override fun actUponTick(currentTick: Int): Boolean {
        if (checkCondition()) {
            reward.applyReward(rewardShip)
            return true
        }
        return false
    }
}