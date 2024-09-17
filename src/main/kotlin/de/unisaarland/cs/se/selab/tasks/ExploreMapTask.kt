package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.Ship

class ExploreMapTask(
    tick: Int,
    id: Int,
    taskShip: Ship,
    reward: Reward,
    rewardShip: Ship,
    corporation: Corporation
): Task(tick, id, taskShip, reward, rewardShip, corporation) {
    override fun checkCondition(): Boolean {
        return true
    }

    override fun actUponTick(currentTick: Int): Boolean {
        return true
    }
}