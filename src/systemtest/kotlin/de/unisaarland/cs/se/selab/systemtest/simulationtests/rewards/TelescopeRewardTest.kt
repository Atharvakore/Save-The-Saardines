package de.unisaarland.cs.se.selab.systemtest.simulationtests.rewards

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests telescope reward
 * */
class TelescopeRewardTest : ExampleSystemTestExtension() {
    override val description = "tests telescope reward"
    override val corporations = "tasksAndRewardsJsons/taskCorporation.Json"
    override val scenario = "tasksAndRewardsJsons/taskScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "TelescopeRewardTest"
    override val maxTicks = 6

    override suspend fun run() {
        skipUntilString("Reward: Task 3: Ship 2 received reward of type TELESCOPE.")
        assertNextLine("Simulation Info: Simulation ended.")
    }
}
