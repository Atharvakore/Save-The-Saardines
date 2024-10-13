package de.unisaarland.cs.se.selab.systemtest.taskTest

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class ExploreUnloadPirate : ExampleSystemTestExtension() {
    override val description = "tests a collect ship needs going to unload but can still perform the task and " +
        "then it got private attack but can still assign rewards"
    override val corporations = "corporationJsons/exploreTaskPirateAttack.json"
    override val scenario = "scenarioJsons/exploreWithPirateAttack.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "find task with no garbage"
    override val maxTicks = 4
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: exploreTaskPirateAttack.json successfully parsed and validated.")
        assertNextLine("Initialization Info: exploreWithPirateAttack.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 13.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 8.")
        skipLines(4)
        assertNextLine("Task: Task 1 of type EXPLORE with ship 2 is added with destination 17.")
        skipLines(3)
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 9.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 2 collected 1000 of garbage CHEMICALS with 1.")
        skipLines(9)
        assertNextLine("Unload: Ship 2 unloaded 1000 of garbage CHEMICALS at harbor 9.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        skipLines(3)
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 17.")
        skipLines(4)
        assertNextLine("Event: Event 1 of type PIRATE_ATTACK happened.")
        assertNextLine("Reward: Task 1: Ship 1 received reward of type TELESCOPE.")
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 1000 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 1000.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
    }
}
