package de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests explore map task
 * */
class ExploreMapTaskTest : ExampleSystemTestExtension() {
    override val description = "tests explore map task"
    override val corporations = "tasksAndRewardsJsons/taskCorporation.Json"
    override val scenario = "tasksAndRewardsJsons/taskScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "ExploreMapTaskTest"
    override val maxTicks = 6

    override suspend fun run() {
        skipUntilString("Event: Event 3 of type RESTRICTION happened.")
        assertNextLine("Task: Task 3 of type EXPLORE with ship 2 is added with destination 65.")
    }
}
