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
        skipUntilString("Event: Event 20 of type RESTRICTION happened.")
        assertNextLine("Reward: Task 4: Ship 5 received reward of type RADIO.")
    }
}
