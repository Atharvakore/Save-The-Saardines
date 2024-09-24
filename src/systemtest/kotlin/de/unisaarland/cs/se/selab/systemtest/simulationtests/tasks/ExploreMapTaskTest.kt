package de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests explore map task
 * */
class ExploreMapTaskTest : ExampleSystemTestExtension() {
    override val description = "tests explore map task"
    override val corporations = "tasksAndRewardsJsons/taskCorporation.Json"
    override val scenario = "tasksAndRewardsJsons/taskScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CooperateTaskTest"
    override val maxTicks = 2

    override suspend fun run() {
        skipUntilString("Simulation Info: Tick 1 started.")
        skipUntilString("Current Drift: OIL 12 with amount 50 drifted from tile 66 to tile 67.")
        assertNextLine("Task: Task 2 of type EXPLORE with ship 1 is added with destination 63.")
    }
}
