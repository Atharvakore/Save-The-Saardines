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
        skipUntilString("Task: Task 3 of type FIND with ship 1 is added with destination 64.")
        assertNextLine("Reward: Task 2: Ship 1 received reward of type TELESCOPE.")
    }
}
