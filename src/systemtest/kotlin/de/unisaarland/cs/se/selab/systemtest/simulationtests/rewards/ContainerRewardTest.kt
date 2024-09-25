package de.unisaarland.cs.se.selab.systemtest.simulationtests.rewards

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests container reward
 * */
class ContainerRewardTest : ExampleSystemTestExtension() {
    override val description = "tests container reward"
    override val corporations = "tasksAndRewardsJsons/taskCorporation.Json"
    override val scenario = "tasksAndRewardsJsons/taskScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "ContainerRewardTest"
    override val maxTicks = 2

    override suspend fun run() {
        skipUntilString("Task: Task 2 of type EXPLORE with ship 2 is added with destination 65.")
        assertNextLine("Reward: Task 1: Ship 1 received reward of type CONTAINER.")
    }
}
