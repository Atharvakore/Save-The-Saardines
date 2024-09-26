package de.unisaarland.cs.se.selab.systemtest.simulationtests.rewards

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests radio reward
 * */
class RadioRewardTest : ExampleSystemTestExtension() {
    override val description = "tests telescope reward"
    override val corporations = "tasksAndRewardsJsons/taskCorporation.Json"
    override val scenario = "tasksAndRewardsJsons/taskScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "RadioRewardTest"
    override val maxTicks = 14
    override suspend fun run() {
        skipUntilString("Reward: Task 9: Ship 5 received reward of type RADIO.")
        assertNextLine("Simulation Info: Tick 13 started.")
    }
}
