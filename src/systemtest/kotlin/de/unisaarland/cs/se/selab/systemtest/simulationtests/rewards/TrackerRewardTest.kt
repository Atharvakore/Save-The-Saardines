package de.unisaarland.cs.se.selab.systemtest.simulationtests.rewards

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests tracker reward
 * */
class TrackerRewardTest : ExampleSystemTestExtension() {
    override val description = "tests tracker reward"
    override val corporations = "tasksAndRewardsJsons/taskCorporation.Json"
    override val scenario = "tasksAndRewardsJsons/taskScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CollectGarbageTaskTest"
    override val maxTicks = 1

    override suspend fun run() {
        skipUntilString("Task: Task 1 of type COLLECT with ship 2 is added with destination 66.")
        assertNextLine("Reward: Task 1: Ship 2 received reward of type TELESCOPE.")
    }
}
