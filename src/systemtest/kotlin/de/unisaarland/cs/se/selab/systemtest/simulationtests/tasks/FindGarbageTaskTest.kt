package de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests find garbage task
 * */
class FindGarbageTaskTest : ExampleSystemTestExtension() {
    override val description = "tests explore map task"
    override val corporations = "tasksAndRewardsJsons/taskCorporation.Json"
    override val scenario = "tasksAndRewardsJsons/taskScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "FindGarbageTaskTest"
    override val maxTicks = 7

    override suspend fun run() {
        skipUntilString("Event: Event 6 of type RESTRICTION happened.")
        assertNextLine("Task: Task 6 of type FIND with ship 3 is added with destination 64.")
    }
}
