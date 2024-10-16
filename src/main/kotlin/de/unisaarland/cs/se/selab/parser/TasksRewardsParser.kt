package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.ships.ShipWithTracker
import de.unisaarland.cs.se.selab.tasks.CollectGarbageTask
import de.unisaarland.cs.se.selab.tasks.ContainerReward
import de.unisaarland.cs.se.selab.tasks.CooperateTask
import de.unisaarland.cs.se.selab.tasks.ExploreMapTask
import de.unisaarland.cs.se.selab.tasks.FindGarbageTask
import de.unisaarland.cs.se.selab.tasks.RadioReward
import de.unisaarland.cs.se.selab.tasks.Reward
import de.unisaarland.cs.se.selab.tasks.TelescopeReward
import de.unisaarland.cs.se.selab.tasks.TrackerReward
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import io.github.oshai.kotlinlogging.KotlinLogging
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

private val logger = KotlinLogging.logger {}

/**
 * Task & Rewards Parser
 */
class TasksRewardsParser(override val accumulator: Accumulator) : JSONParser {
    private val id: String = "id"

    /** Parse function for Tasks*/
    fun parseTasks(taskJSON: String): Boolean {
        try {
            val tasks = JSONObject(taskJSON).getJSONArray("tasks")
            return createTasks(tasks)
        } catch (e: IOException) {
            logger.error { e }
        }
        return false
    }

    private fun createTasks(tasks: JSONArray): Boolean {
        for (task in tasks) {
            if (task != null && !validateTask(task as JSONObject)) {
                return false
            }
        }
        return true
    }

    private fun validateTask(task: JSONObject): Boolean {
        val id = task.getInt(id)
        val uniqueId: Boolean = id >= 0 && accumulator.tasks[id] == null
        val taskShip: Ship? = accumulator.ships[task.getInt("shipID")]
        val rewardShip: Ship? = accumulator.ships[task.getInt("rewardShipID")]
        val reward: Reward? = accumulator.rewards[task.getInt("rewardID")]
        val rewardExists: Boolean = reward != null
        val taskShipExists: Boolean = taskShip != null
        val rewardShipExists: Boolean = rewardShip != null
        val sameCorpRewardAndTask: Boolean = taskShip?.owner == rewardShip?.owner
        var condition: Boolean = uniqueId && rewardExists && rewardShipExists
        condition = condition && taskShipExists && sameCorpRewardAndTask
        val targetTile: Tile? = accumulator.tiles[task.getInt("targetTile")]
        if (targetTile == null) {
            condition = false
        }
        return if (condition) {
            createTask(task)
        } else {
            false
        }
    }

    private fun createTask(task: JSONObject): Boolean {
        val id = task.getInt(id)
        val type = task.getString(TYPE)
        val tick = task.getInt("tick")
        val ship = accumulator.ships[task.getInt("shipID")]
        val rShip = requireNotNull(accumulator.ships[task.getInt("rewardShipID")])
        val reward: Reward? = accumulator.rewards[task.getInt("rewardID")]
        val tile: Tile = requireNotNull(accumulator.tiles[task.getInt("targetTile")])
        var returnCond = false
        when (type) {
            "COLLECT" -> {
                if (reward is ContainerReward) {
                    val taskObj = CollectGarbageTask(tick, id, requireNotNull(ship), reward, rShip, tile)
                    accumulator.addTask(id, taskObj)
                    taskObj.taskShip.owner.tasks.add(taskObj)
                    returnCond = true
                }
            }

            "EXPLORE" -> {
                if (reward is TelescopeReward) {
                    val taskObj = ExploreMapTask(tick, id, requireNotNull(ship), reward, rShip, tile)
                    taskObj.taskShip.owner.tasks.add(taskObj)
                    accumulator.addTask(id, taskObj)
                    returnCond = true
                }
            }

            "FIND" -> {
                if (reward is TrackerReward) {
                    val taskObj = FindGarbageTask(tick, id, requireNotNull(ship), reward, rShip, tile)
                    taskObj.taskShip.owner.tasks.add(taskObj)
                    accumulator.addTask(id, taskObj)
                    returnCond = true
                }
            }

            "COOPERATE" -> {
                var condition = false
                if (tile is Shore) {
                    condition = tile.harbor
                }
                if (reward is RadioReward && condition) {
                    val taskObj = CooperateTask(
                        tick,
                        id,
                        requireNotNull(ship),
                        reward,
                        rShip,
                        tile
                    )
                    taskObj.myCorp = requireNotNull(ship).owner
                    taskObj.otherCorp = accumulator.corporations.values.filter { it.ownedHarbors.contains(tile) }
                    accumulator.addTask(id, taskObj)
                    taskObj.taskShip.owner.tasks.add(taskObj)
                    returnCond = true
                }
            }
        }
        return returnCond
    }

    /** Parse function For rewards*/
    fun parseRewards(rewardJSON: String): Boolean {
        try {
            val rewards = JSONObject(rewardJSON).getJSONArray("rewards")
            return createRewards(rewards)
        } catch (e: IOException) {
            logger.error { e }
        }
        return false
    }

    private fun createRewards(rewards: JSONArray): Boolean {
        for (reward in rewards) {
            if (!validateReward((reward ?: error("Reward should exist...")) as JSONObject)) {
                return false
            }
        }
        return true
    }

    private fun validateReward(reward: JSONObject): Boolean {
        val id = reward.getInt(id)
        val uniqueId: Boolean = id >= 0 && accumulator.rewards[id] == null
        val type: String = reward.getString(TYPE)
        var condition: Boolean = true
        when (type) {
            "TRACKER", "RADIO" -> condition = reward.keySet() == requiredByDefault
            "CONTAINER" -> condition = reward.keySet() == requiredByContainer
            "TELESCOPE" -> condition = reward.keySet() == requiredByTelescope
        }
        if (uniqueId && condition) {
            return createReward(reward)
        }

        return false
    }

    private fun createReward(reward: JSONObject): Boolean {
        val rewardId: Int = reward.getInt(id)
        val rewardType: String = reward.getString(TYPE)
        when (rewardType) {
            "TELESCOPE" -> {
                val visibility: Int = reward.getInt(VISIBILITYRANGE)
                accumulator.addReward(rewardId, TelescopeReward(rewardId, ScoutingShip(0), visibility))
            }

            "RADIO" -> {
                accumulator.addReward(rewardId, RadioReward(rewardId, CoordinatingShip(0)))
            }

            "CONTAINER" -> {
                val capacity: Int = reward.getInt(CAPACITY)
                val garbageType: GarbageType = GarbageType.valueOf(reward.getString(GARBAGETYPE))
                val container = Container(garbageType, capacity)
                val collectingShip = CollectingShip(mutableListOf(container))
                accumulator.addReward(rewardId, ContainerReward(rewardId, collectingShip, container))
            }

            "TRACKER" -> {
                accumulator.addReward(rewardId, TrackerReward(rewardId, ShipWithTracker()))
            }
        }
        return true
    }

    /** All Strings required*/
    companion object {
        const val TYPE = "type"
        const val ID = "id"
        const val CAPACITY = "capacity"
        const val GARBAGETYPE = "garbageType"
        const val VISIBILITYRANGE = "visibilityRange"
        val requiredByDefault = setOf(ID, TYPE)
        val requiredByTelescope = requiredByDefault + VISIBILITYRANGE
        val requiredByContainer = requiredByDefault + GARBAGETYPE + CAPACITY
    }
}
