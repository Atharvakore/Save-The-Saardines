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
    override val name = "CollectGarbageTaskTest"
    override val maxTicks = 4
    override suspend fun run() {
        skipUntilString("Task: Task 4 of type COOPERATE with ship 4 is added with destination 66.")
        assertNextLine("Reward: Task 4: Ship 3 received reward of type TRACKER.")
    }
}
