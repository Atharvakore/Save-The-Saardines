package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.logger.LoggerEventsAndTasks
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.Tile
/** Class for Cooperating Task*/
/** Task regarding cooperation between companies. */
class CooperateTask(
    tick: Int,
    id: Int,
    taskShip: Ship,
    reward: Reward,
    rewardShip: Ship,
    val destinationHomeHarbor: Tile
) : Task(tick, id, taskShip, reward, rewardShip) {

    lateinit var myCorp: Corporation
    lateinit var otherCorp: List<Corporation>
    var taskShipArrived: Int? = null
    override fun toString(): String {
        return "COOPERATE"
    }

    private fun putGarbage(x: List<Garbage>, it: Tile) {
        x.forEach { garbage ->
            otherCorp.forEach { other -> other.partnerGarbage[garbage.id] = it }
        }
    }
    override fun checkCondition(): Boolean {
        if (taskShip.position.pos == destinationHomeHarbor.pos && taskShipArrived == null) {
            val myCorpTiles = myCorp.ownedShips.map { it.position }.filter { it.garbage.isNotEmpty() }
            myCorpTiles.forEach {
                val x = it.garbage
                putGarbage(x, it)
            }
            taskShip.isInWayToRefuelOrUnload = true
            taskShipArrived = -1
            return false
        }
//        if (taskShip.position.pos == destinationHomeHarbor.pos && taskShipArrived == -1) {
//            taskShip.isInWayToRefuelOrUnload = false
//            taskShipArrived = 1
//            return false
//        }
        return taskShipArrived == -1
    }

    override fun actUponTick(currentTick: Int): Boolean {
        if (currentTick == tick) {
            LoggerEventsAndTasks.logTaskStart(id, "COOPERATE", taskShip.id, destinationHomeHarbor.id)
        }
        if (checkCondition() && currentTick > tick) {
            LoggerEventsAndTasks.logRewardReceived(id, rewardShip.id, reward)
            reward.applyReward(rewardShip)
            taskShip.owner.tasks.remove(this)
            return true
        }
        return false
    }

    override fun getGoal(): Tile {
        return destinationHomeHarbor
    }
}
