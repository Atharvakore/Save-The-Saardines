package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.events.Event
import de.unisaarland.cs.se.selab.events.OilSpill
import de.unisaarland.cs.se.selab.events.PirateAttack
import de.unisaarland.cs.se.selab.events.Restriction
import de.unisaarland.cs.se.selab.events.Storm
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.Reward
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class ScenarioJSONParser(override val accumulator: Accumulator): JSONParser {
     fun parseEvents(eventsFile: String): Boolean {
        try {
            val events = JSONObject(File(eventsFile).readText()).getJSONArray("events")
            return createEvents(events)
        } catch (error: Exception){
            return false
        }
    }
    private fun createEvents(events: JSONArray): Boolean {
        for (event in events){
            if (!validateEvent(event as JSONObject)){
                return false
            }
        }
        return true
    }

    private fun validateEvent(event: JSONObject): Boolean {
        val uniqueId: Boolean = accumulator.getEventById(event.getInt("id")) == null
        //val event : Boolean = accumulator.getShipsbyId(event.getInt("shipID")) != null
        // TODO: Conditions to add
        if (uniqueId){
            return createEvent(event)
        }
        return false
    }
    private fun createEvent(event: JSONObject): Boolean {
        val eventId = event.getInt("id")
        val eventType: String = event.getString("type")
        val eventTick: Int = event.getInt("tick")
        when (eventType) {
            "STORM" -> {
                val eventLocation: Int = event.getInt("location")
                val eventTile: Tile? = accumulator.getTileById(eventLocation) ?: return false
                val eventRadius: Int = event.getInt("radius")
                val eventSpeed: Int =  event.getInt("speed")
                val eventDirection: Direction? = Direction.getDirection(event.getInt("direction"))
                val storm: Storm = Storm(eventId, eventTick, accumulator.getMap(), eventTile, eventRadius)
                storm.setSpeed(eventSpeed)
                storm.setDirection(eventDirection)
                accumulator.addEvent(eventId, storm)
            }
            "RESTRICTION" -> {
                val eventDuration: Int = event.getInt("duration")
                val eventLocation: Int = event.getInt("location")
                val eventTile: Tile = accumulator.getTileById(eventLocation) ?: return false
                val restriction: Event = Restriction(eventId, eventTick, accumulator.getMap(), eventTile, eventDuration)
                accumulator.addEvent(eventId, restriction)

            }
            "OIL_SPILL" -> {
                val eventLocation: Int = event.getInt("location")
                val eventTile: Tile = accumulator.getTileById(eventLocation) ?: return false
                val eventRadius: Int = event.getInt("radius")
                val eventAmount: Int = event.getInt("amount")
                val oilSpill = OilSpill(eventId, eventTick, accumulator.getMap(), eventTile, eventRadius, eventAmount)
                accumulator.addEvent(eventId, oilSpill)
            }
            "PIRATE_ATTACK" -> {
                val eventShip: Int = event.getInt("shipID")
                val ship: Ship = accumulator.getShipsById(eventShip) ?: return false
                val shipCorp: Corporation = ship.getOwner()
                accumulator.addEvent(eventId, PirateAttack(eventId, eventTick, ship, shipCorp))
            }
        }
        return true
    }
    fun parseGarbage(garbageJSON: String): Boolean {
        try {
            val garbage = JSONObject(File(garbageJSON).readText()).getJSONArray("garbage")
            return createGarbage(garbage)
        } catch (error: Exception){
            return false
        }
    }
    private fun createGarbage(garbage: JSONArray): Boolean {
        for (gar in garbage){
            if (!validateGarbage(gar as JSONObject)){
                return false
            }
        }
        return true
    }

    private fun validateGarbage(garbage: JSONObject): Boolean {
        val uniqueId: Boolean=  accumulator.getGarbageById(garbage.getInt("id")) == null
        val location: Tile? = accumulator.getTileById(garbage.getInt("location"))
        val locationExists: Boolean =  location != null
        var locationIsNotLand: Boolean = location is Shore || location is ShallowOcean
        if (garbage.getString("type") != "CHEMICALS"){
            locationIsNotLand = locationIsNotLand || location is DeepOcean
        }
        if (uniqueId && locationExists && locationIsNotLand){
            return createGarbageObj(garbage)

        }
        return false
    }
    private fun createGarbageObj(garbage: JSONObject): Boolean {
        val garbageId: Int = garbage.getInt("id")
        val garbageType: GarbageType = GarbageType.valueOf(garbage.getString("type"))
        val garbageLocation: Tile = accumulator.getTileById(garbage.getInt("location")) ?: return false
        val amount: Int = garbage.getInt("amount")
        when (garbageType) {
            GarbageType.PLASTIC -> {
                accumulator.addGarbage(garbageId, )
            }
            GarbageType.OIL-> {
                accumulator.addGarbage(garbageId, )
            }
            GarbageType.CHEMICALS -> {
                accumulator.addGarbage(garbageId, )
            }
        }
    }
    public fun parseTasks(taskJSON: String): Boolean {
        try {
            val tasks = JSONObject(File(taskJSON).readText()).getJSONArray("tasks")
            return createTasks(tasks)
        } catch (error: Exception){
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
        val uniqueId: Boolean = accumulator.getTaskById(task.getInt("id")) == null
        val targetTile: Tile = accumulator.getTileById(task.getInt("targetTile")) ?: return false
        val targetIsNotShore: Boolean = targetTile !is Shore
        val taskShip: Ship? = accumulator.getShipsById(task.getInt("shipID"))
        val rewardShip: Ship? =  accumulator.getShipsById(task.getInt("rewardShipID"))
        val rewardExists: Boolean = accumulator.getRewardById(task.getInt("rewardID")) != null
        val taskShipExists: Boolean = taskShip != null
        val rewardShipExists: Boolean = rewardShip != null
        val sameCorpRewardAndTask: Boolean = taskShip?.getOwner() == rewardShip?.getOwner()
        var condition: Boolean = uniqueId && targetIsNotShore && rewardExists
        condition = condition && rewardShipExists && taskShipExists && sameCorpRewardAndTask
        if (condition){
            return createTask(task)
        }
        return false
    }
    private fun createTask(task: JSONObject): Boolean {
        val taskId = task.getInt("id")
        val taskType = task.getString("type")
        val taskTick = task.getInt("tick")
        val taskShip = accumulator.getShipsById(task.getInt("shipID"))
        val targetTile = accumulator
        when (task.getString("type")) {
            "COLLECT" -> {

            }
            "EXPLORE" -> {

            }
            "FIND" -> {}
            "COOPERATE" -> {}
        }
    }
    public fun parseRewards(rewardJSON: String): Boolean {
        try {
            val rewards = JSONObject(File(rewardJSON).readText()).getJSONArray("rewards")
            return createRewards(rewards)
        } catch (error: Exception){
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
        val uniqueId: Boolean = accumulator.getRewardById(reward.getInt("id")) == null
        // TODO: Validation condition
        if (uniqueId) {
            return createReward(reward)
        }
        return false
    }
    private fun createReward(reward: JSONObject): Boolean {
        val rewardId: Int = reward.getInt("id")
        val rewardType: String = reward.getString("type")

    }
}
