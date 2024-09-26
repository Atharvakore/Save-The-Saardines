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
    override val maxTicks = 15

    override suspend fun run() {
        skipUntilString("Event: Event 9 of type RESTRICTION happened.")
        assertNextLine("Task: Task 9 of type COOPERATE with ship 1 is added with destination 52.")
    }
}
