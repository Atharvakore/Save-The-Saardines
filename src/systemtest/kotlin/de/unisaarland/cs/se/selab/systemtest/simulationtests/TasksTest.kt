package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTestAssertionError
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import de.unisaarland.cs.se.selab.systemtest.utils.Logs

/**
 * Tests Tasks
 */
class TasksTest : ExampleSystemTestExtension() {
    override val description = "tests Task Assignment and Working"
    override val corporations = "corporationJsons/stormEventCorporation.json"
    override val scenario = "scenarioJsons/TaskTest.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "TaskTest"
    override val maxTicks = 25

    override suspend fun run() {
        val expectedString = "Simulation Info: Simulation started."
        if (skipUntilLogType(Logs.SIMULATION_INFO) != expectedString) {
            throw SystemTestAssertionError("Simulation Info: Tick 15 started.")
        }
        val corporationAction = "Corporation Action: Corporation 1 is starting to move its ships."
        val collect = "Corporation Action: Corporation 1 is starting to collect garbage."
        val coperate = "Corporation Action: Corporation 1 is starting to cooperate with other corporations."
        val refuel = "Corporation Action: Corporation 1 is starting to refuel."
        val actions = "Corporation Action: Corporation 1 finished its actions."
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine(corporationAction)
        assertNextLine(collect)
        assertNextLine(coperate)
        assertNextLine(refuel)
        assertNextLine(actions)
        assertNextLine("Simulation Info: Tick 1 started.")
        assertNextLine(corporationAction)
        assertNextLine(collect)
        assertNextLine(coperate)
        assertNextLine(refuel)
        assertNextLine(actions)
        assertNextLine("Task: Task 5 of type EXPLORE with ship 2 is added with destination 27.")
        assertNextLine("Simulation Info: Tick 2 started.")
        assertNextLine(corporationAction)
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 24.")
        assertNextLine(collect)
        assertNextLine(coperate)
        assertNextLine(refuel)
        assertNextLine(actions)
        assertNextLine("Simulation Info: Tick 3 started.")
        assertNextLine(corporationAction)
        assertNextLine("Ship Movement: Ship 2 moved with speed 20 to tile 26.")
        assertNextLine(collect)
        assertNextLine(coperate)
        assertNextLine(refuel)
        assertNextLine(actions)
        assertNextLine("Simulation Info: Tick 4 started.")
        assertNextLine(corporationAction)
        assertNextLine("Ship Movement: Ship 2 moved with speed 30 to tile 27.")
        assertNextLine(collect)
        assertNextLine("Garbage Collection: Ship 2 collected 1000 of garbage OIL with 2.")
        assertNextLine(coperate)
        assertNextLine(refuel)
        assertNextLine(actions)
        assertNextLine("Reward: Task 5: Ship 2 received reward of type TELESCOPE.")
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 1000 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 1000.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
    }
}
