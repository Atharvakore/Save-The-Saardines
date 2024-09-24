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
    override val name = "CooperateTaskTest"
    override val maxTicks = 3

    override suspend fun run() {
        skipUntilString("Simulation Info: Tick 2 started.")
        skipUntilString("Current Drift: OIL 13 with amount 50 drifted from tile 66 to tile 67.")
        assertNextLine("Task: Task 3 of type FIND with ship 3 is added with destination 36.")
    }
}
