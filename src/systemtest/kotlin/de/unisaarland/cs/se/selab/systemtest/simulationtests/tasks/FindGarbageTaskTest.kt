package de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests find garbage task
 * */
class FindGarbageTaskTest : ExampleSystemTestExtension() {
    override val description = "tests explore map task"
    override val corporations = "tasksAndRewardsJsons/taskCorporation.Json"
    override val scenario = "tasksAndRewardsJsons/taskScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "FindGarbageTaskTest"
    override val maxTicks = 15

    override suspend fun run() {
        skipUntilString("Event: Event 6 of type RESTRICTION happened.")
        assertNextLine("Task: Task 6 of type FIND with ship 3 is added with destination 64.")
        assertNextLine("Simulation Info: Tick 7 started.")
        //
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 100 to tile 72.")
        assertNextLine("Ship Movement: Ship 3 moved with speed 10 to tile 67.")
        assertNextLine("Ship Movement: Ship 5 moved with speed 20 to tile 66.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Corporation Action: Corporation 2 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 6 moved with speed 100 to tile 72.")
        assertNextLine("Corporation Action: Corporation 2 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 2 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 2 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 2 finished its actions.")
        assertNextLine("Current Drift: Ship 5 drifted from tile 66 to tile 67.")
        assertNextLine("Current Drift: Ship 3 drifted from tile 67 to tile 68.")


    }
}
