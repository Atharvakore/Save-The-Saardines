package de.unisaarland.cs.se.selab.parser

import kotlinx.serialization.json.JsonElement
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class ScenarioJSONParser(override val accumulator: Accumulator): JSONParser {
    public fun parseEvents(eventsFile: String): Boolean{
        try {
            val events = JSONObject(File(eventsFile).readText()).getJSONArray("events")
            return createEvents(events)
        }
        catch(error: Exception){
            return False
        }

    }
    public fun createEvents(events: JSONArray): Boolean{
        for (event in events){
            if (validateEvent(event as JSONObject)){
                return createEvent(event)
            }
        }
        return false
    }

    private fun validateEvent(event: JSONObject): Boolean{
        val uniqueId: Boolean = accumulator.getEventById(event.getInt("id")) == null
        //val event : Boolean = accumulator.getShipsbyId(event.getInt("shipID")) != null
        // TODO: Conditions to add
        if (uniqueId){
            val eventObj: Event = createEvent(event)
            accumulator.addEvent(event.getInt("id"), eventObj)

            return true
        }
        return false
    }
    public fun parseGarbage(garbageJSON: String): Boolean{
        try {
            val garbage = JSONObject(File(garbageJSON).readText()).getJSONArray("garbage")
            return createGarbage(garbage)
        }
        catch(error: Exception){
            return False
        }

    }
    public fun createGarbage(garbage: JSONArray): Boolean{
        for (gar in garbage){
            if (validateGarbage(gar as JSONObject)){
                return createEvent(gar)
            }
        }
        return false
    }

    private fun validateGarbage(garbage: JSONObject): Boolean{
        val uniqueId: Boolean=  accumulator.getGarbageById(garbage.getInt("id")) == null
        val location: Tile? = accumulator.getTileById(garbage.getInt("location"))
        val locationExists: Boolean=  location != null
        val locationIsNotLand: Boolean = location !is Land
        if (uniqueId && locationExists && locationIsNotLand){
            val garbageObj: Garbage = createGarbage(garbage)
            accumulator.addGarbage(garbageObj.getId(), garbageObj)
            return true
        }
        return false
    }


    public fun parseTasks(taskJSON: String): Boolean{}
    public fun parseRewards(rewardJSON: String): Boolean{

    }



    private fun validateTasks(tasks: List<JSONObject>): Boolean{
        for (task in tasks){validateTask(task)}
    }
    private fun validateTask(task: JSONObject): Boolean{
        val uniqueId: Boolean = accumulator.getTaskById(task.getInt("id")) == null
        if (uniqueId){
            val taskObj : Task = createTask(task)
            accumulator.addTask(taskObj.getId(), taskObj)
            return true
        }
        return false
    }
    private fun validateRewards(rewards: List<JSONObject>): Boolean{
        for (reward in rewards){validateReward(reward)}
    }
    private fun validateReward(reward: JSONObject): Boolean{
        val uniqueId: Boolean = accumulator.getRewardById(reward.getInt("id")) == null
        // TODO: Validation condition
        if (uniqueId){
            val r : Reward = createReward(reward)
            accumulator.addReward(r.getId(), r)
            return true
        }
        return false
    }
    private fun createGarbage(garbage: JSONObject): Garbage{}
    private fun createEvent(event: JSONObject): Event{}
    private fun createReward(reward: JSONObject): Reward{}
    private fun createTask(task: JSONObject): Task{}

}
