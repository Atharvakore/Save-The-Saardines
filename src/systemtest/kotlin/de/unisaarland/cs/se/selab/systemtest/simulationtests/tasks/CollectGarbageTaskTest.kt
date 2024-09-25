package de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests collect garbage task
 * */
class CollectGarbageTaskTest : ExampleSystemTestExtension() {
    override val description = "tests collect garbage task"
    override val corporations = "tasksAndRewardsJsons/taskCorporation.Json"
    override val scenario = "tasksAndRewardsJsons/taskScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CollectGarbageTaskTest"
    override val maxTicks = 6

    override suspend fun run() {
        skipUntilString("Event: Event 15 of type RESTRICTION happened.")
        assertNextLine("Task: Task 1 of type COLLECT with ship 1 is added with destination 66.")
    }
}
