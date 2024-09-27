package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests general garabge collecting
 * */
class TaskAndRewardsTest : ExampleSystemTestExtension() {
    override val description = "tests tasks and rewards"
    override val corporations = "tasksAndRewardsTestJsons/corporation.json"
    override val scenario = "tasksAndRewardsTestJsons/scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "TaskAndRewardsTest"
    override val maxTicks = 1

    override suspend fun run() {
        assertNextLine("Initialization Info: map_medium_01.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
    }
}
