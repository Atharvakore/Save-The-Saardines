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
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class TasksRewardsParser(override val accumulator: Accumulator) : JSONParser {
    private var id: String = "id"
    public fun parseTasks(taskJSON: String): Boolean {
        try {
            val tasks = JSONObject(File(taskJSON).readText()).getJSONArray("tasks")
            return createTasks(tasks)
        } catch (error: Exception) {
            return false
        }
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
        val uniqueId: Boolean = accumulator.getTaskById(task.getInt(id)) == null
        val targetTile: Tile = accumulator.getTileById(task.getInt("targetTile")) ?: return false
        val taskShip: Ship? = accumulator.getShipsById(task.getInt("shipID"))
        val rewardShip: Ship? = accumulator.getShipsById(task.getInt("rewardShipID"))
        val reward: Reward? = accumulator.getRewardById(task.getInt("rewardID"))
        val rewardExists: Boolean = reward != null
        val taskShipExists: Boolean = taskShip != null
        val rewardShipExists: Boolean = rewardShip != null
        val sameCorpRewardAndTask: Boolean = taskShip?.getOwnerCorporation() == rewardShip?.getOwnerCorporation()
        var condition: Boolean = uniqueId && rewardExists && rewardShipExists
        condition = condition && taskShipExists && sameCorpRewardAndTask
        if (condition) {
            return createTask(task)
        }
        return false
    }
    private fun createTask(task: JSONObject): Boolean {
        val taskId = task.getInt(id)
        val taskType = task.getString("type")
        val taskTick = task.getInt("tick")
        val taskShip = accumulator.getShipsById(task.getInt("shipID"))
        val rewardShip = accumulator.getShipsById(task.getInt("rewardShipID"))
        val taskCorporation = rewardShip!!.getOwnerCorporation()
        val reward: Reward? = accumulator.getRewardById(task.getInt("rewardID"))
        val targetTile: Tile = accumulator.getTileById(task.getInt("targetTile"))!!
        var returnCond = false
        when (taskType) {
            "COLLECT" -> {
                if (reward is ContainerReward) {
                    val taskObj = CollectGarbageTask(
                        taskTick,
                        taskId,
                        taskShip!!,
                        reward,
                        rewardShip,
                        taskCorporation,
                        targetTile!!
                    )
                    accumulator.addTask(taskId, taskObj)
                    returnCond = true
                }
            }
            "EXPLORE" -> {
                if (reward is TelescopeReward) {
                    val taskObj = ExploreMapTask(taskTick, taskId, taskShip!!, reward, rewardShip, taskCorporation, targetTile!!)
                    accumulator.addTask(taskId, taskObj)
                    returnCond = true
                }
            }
            "FIND" -> {
                if (reward is TrackerReward) {
                    val taskObj = FindGarbageTask(taskTick, taskId, taskShip!!, reward, rewardShip, taskCorporation, targetTile!!)
                    accumulator.addTask(taskId, taskObj)
                    returnCond = true
                }
            }
            "COOPERATE" -> {
                var condition: Boolean = false
                if (targetTile is Shore) {
                    condition = targetTile.harbor
                }
                if (reward is RadioReward && condition) {
                    val taskObj = CooperateTask(taskTick, taskId, taskShip!!, reward, rewardShip, taskCorporation, targetTile!!)
                    accumulator.addTask(taskId, taskObj)
                    returnCond = true
                }
            }
        }
        return returnCond
    }
    public fun parseRewards(rewardJSON: String): Boolean {
        try {
            val rewards = JSONObject(File(rewardJSON).readText()).getJSONArray("rewards")
            return createRewards(rewards)
        } catch (error: Exception) {
            return false
        }
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
        val uniqueId: Boolean = accumulator.getRewardById(reward.getInt(id)) == null
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
                val container: Container = Container(garbageType, capacity)
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
