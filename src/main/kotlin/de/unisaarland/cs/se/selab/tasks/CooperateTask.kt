package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Tile

/** Task regarding cooperation between companies. */
class CooperateTask(
    tick: Int,
    id: Int,
    taskShip: Ship,
    reward: Reward,
    rewardShip: Ship,
    corporation: Corporation,
    val destinationHomeHarbor: Tile
): Task(tick, id, taskShip, reward, rewardShip, corporation) {

    override fun toString(): String {
        return "Cooperate with Other Corporation"
    }
    override fun checkCondition(): Boolean {
        return taskShip.getPosition().pos == destinationHomeHarbor.pos
    }

    override fun actUponTick(currentTick: Int): Boolean {
        if (checkCondition()) {
            reward.applyReward(rewardShip)
            return true
        }
        return false
    }

    override fun getGoal(): Tile {
        return destinationHomeHarbor
    }
}