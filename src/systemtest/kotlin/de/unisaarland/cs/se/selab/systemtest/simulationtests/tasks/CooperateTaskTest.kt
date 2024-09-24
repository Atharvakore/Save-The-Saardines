package de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests cooperate task
 * */
class CooperateTaskTest : ExampleSystemTestExtension() {
    override val description = "tests cooperate task"
    override val corporations = "tasksAndRewardsJsons/taskCorporation.Json"
    override val scenario = "tasksAndRewardsJsons/taskScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CooperateTaskTest"
    override val maxTicks = 4

    override suspend fun run() {
        skipUntilString("Simulation Info: Tick 3 started.")
        skipUntilString("Current Drift: OIL 14 with amount 50 drifted from tile 66 to tile 67.")
        assertNextLine("Task: Task 4 of type COOPERATE with ship 4 is added with destination 66.")
    }
}
