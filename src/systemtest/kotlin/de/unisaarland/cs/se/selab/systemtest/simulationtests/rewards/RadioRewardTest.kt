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
    override val maxTicks = 6
    override suspend fun run() {
        skipUntilString("Task: Task 4 of type COOPERATE with ship 1 is added with destination 52.")
        assertNextLine("Reward: Task 4: Ship 1 received reward of type RADIO.")
    }
}
