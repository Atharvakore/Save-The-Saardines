package de.unisaarland.cs.se.selab.parser

import org.json.JSONObject

class ScenarioJSONParser(accumulator: Accumulator) {
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
    private fun validateGarbage(garbage: JSONObject): Boolean{}
    private fun validateEvents(events:List<JSONObject>): Boolean{
        for (event in events){
            validateEvent(event)
        }
    }
    private fun validateEvent(event: JSONObject): Boolean{

    }
    private fun validateTasks(tasks: List<JSONObject>): Boolean{
        for (task in tasks){validateTask(task)}
    }
    private fun validateTask(task: JSONObject): Boolean{

    }
    private fun validateRewards(rewards: List<JSONObject>): Boolean{
        for (reward in rewards){validateReward(reward)}
    }
    private fun validateReward(reward: JSONObject): Boolean{}
    private fun createGarbage(garbage: JSONObject): Garbage{}
    private fun createEvent(event: JSONObject): Event{}
    private fun createReward(reward: JSONObject): Reward{}
    private fun  createTask(task: JSONObject): Task{}
}
