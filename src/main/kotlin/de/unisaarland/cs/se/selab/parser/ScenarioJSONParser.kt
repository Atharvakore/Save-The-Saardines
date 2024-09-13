package de.unisaarland.cs.se.selab.parser

import org.json.JSONObject

class ScenarioJSONParser(override val accumulator: Accumulator): JSONParser {
    public fun parseEvent(EventJSON: String): Boolean {

    }



    public fun parseEvents(EventJSON: String): Boolean{

    }
    public fun parseGarbage(garbageJSON: String): Boolean{

    }
    public fun parseTasks(taskJSON: String): Boolean{}
    public fun parseRewards(rewardJSON: String): Boolean{

    }
    private fun validateGarbages(garbages:List<JSONObject>): Boolean{
        for (garbage in garbages){
            validateGarbage(garbage)
        }
    }
    private fun validateGarbage(garbage: JSONObject): Boolean{
        val uniqueId: Boolean=  accumulator.getGarbageById(garbage.getInt("id")) == null
        val locationExists: Boolean= accumulator.getTileById(garbage.getInt("location")) != null
        if (uniqueId && locationExists){
            val garbageObj: Garbage = createGarbage(garbage)
            accumulator.addGarbage(garbageObj.getId(), garbageObj)
            return true
        }
        return false
    }
    private fun validateEvents(events:List<JSONObject>): Boolean{
        for (event in events){
            validateEvent(event)
        }
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
