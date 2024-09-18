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
import java.io.FileNotFoundException
import java.io.IOException

private val logger = KotlinLogging.logger {}

/**
 * Task & Rewards Parser
 */
class TasksRewardsParser(override val accumulator: Accumulator) : JSONParser {
    private var id: String = "id"

    /** Parse function for Tasks*/
    fun parseTasks(taskJSON: String): Boolean {
        try {
            val tasks = JSONObject(taskJSON).getJSONArray("tasks")
            return createTasks(tasks)
        } catch (e: IOException) {
            logger.error { e }
        } catch (error: FileNotFoundException) {
            logger.error { error }
        }
        return false
    }

    private fun createTasks(tasks: JSONArray): Boolean {
        for (task in tasks) {
            if (!validateTask(task as JSONObject)) {
                return false
            }
        }
        return true
    }

    private fun validateTask(task: JSONObject): Boolean {
        val uniqueId: Boolean = accumulator.tasks[task.getInt(id)] == null
        val taskShip: Ship? = accumulator.ships[task.getInt("shipID")]
        val rewardShip: Ship? = accumulator.ships[task.getInt("rewardShipID")]
        val reward: Reward? = accumulator.rewards[task.getInt("rewardID")]
        val rewardExists: Boolean = reward != null
        val taskShipExists: Boolean = taskShip != null
        val rewardShipExists: Boolean = rewardShip != null
        val sameCorpRewardAndTask: Boolean = taskShip?.owner == rewardShip?.owner
        var condition: Boolean = uniqueId && rewardExists && rewardShipExists
        condition = condition && taskShipExists && sameCorpRewardAndTask
        val targetTile: Tile? = accumulator.getTileById(task.getInt("targetTile"))
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
        val taskId = task.getInt(id)
        val taskType = task.getString("type")
        val taskTick = task.getInt("tick")
        val taskShip = accumulator.ships[task.getInt("shipID")]
        val rewardShip = accumulator.ships[task.getInt("rewardShipID")]!!
        val reward: Reward? = accumulator.rewards[task.getInt("rewardID")]
        val targetTile: Tile = accumulator.getTileById(task.getInt("targetTile"))!!
        var returnCond = false
        when (taskType) {
            "COLLECT" -> {
                if (reward is ContainerReward) {
                    val taskObj = CollectGarbageTask(taskTick, taskId, taskShip!!, reward, rewardShip, targetTile)
                    accumulator.addTask(taskId, taskObj)
                    returnCond = true
                }
            }

            "EXPLORE" -> {
                if (reward is TelescopeReward) {
                    val taskObj = ExploreMapTask(taskTick, taskId, taskShip!!, reward, rewardShip, targetTile)
                    accumulator.addTask(taskId, taskObj)
                    returnCond = true
                }
            }

            "FIND" -> {
                if (reward is TrackerReward) {
                    val taskObj = FindGarbageTask(taskTick, taskId, taskShip!!, reward, rewardShip, targetTile)
                    accumulator.addTask(taskId, taskObj)
                    returnCond = true
                }
            }

            "COOPERATE" -> {
                var condition = false
                if (targetTile is Shore) {
                    condition = targetTile.harbor
                }
                if (reward is RadioReward && condition) {
                    val taskObj = CooperateTask(
                        taskTick,
                        taskId,
                        taskShip!!,
                        reward,
                        rewardShip,
                        targetTile
                    )
                    accumulator.addTask(taskId, taskObj)
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
        } catch (error: FileNotFoundException) {
            logger.error { error }
        }
        return false
    }

    private fun createRewards(rewards: JSONArray): Boolean {
        for (reward in rewards) {
            if (!validateReward(reward as JSONObject)) {
                return false
            }
        }
        return true
    }

    private fun validateReward(reward: JSONObject): Boolean {
        val uniqueId: Boolean = accumulator.rewards[reward.getInt(id)] == null
        if (uniqueId) {
            return createReward(reward)
        }
        return false
    }

    private fun createReward(reward: JSONObject): Boolean {
        val rewardId: Int = reward.getInt(id)
        val rewardType: String = reward.getString("type")
        when (rewardType) {
            "TELESCOPE" -> {
                val visibility: Int = reward.getInt("visibilityRange")
                accumulator.addReward(rewardId, TelescopeReward(rewardId, ScoutingShip(visibility), visibility))
            }

            "RADIO" -> {
                accumulator.addReward(rewardId, RadioReward(rewardId, CoordinatingShip(0)))
            }

            "CONTAINER" -> {
                val capacity: Int = reward.getInt("capacity")
                val garbageType: GarbageType = GarbageType.valueOf(reward.getString("garbageType"))
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
}
