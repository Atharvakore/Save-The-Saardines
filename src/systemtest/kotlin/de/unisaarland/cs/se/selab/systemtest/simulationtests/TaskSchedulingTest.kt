package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * Tests scheduling of Tasks
 */
class TaskSchedulingTest : ExampleSystemTestExtension() {
    override val description = "tests scheduling task"
    override val corporations = "schedulingTaskTestJsons/corporation.json"
    override val scenario = "schedulingTaskTestJsons/scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "SchedulingTaskTest"
    override val maxTicks = 4

    override suspend fun run() {
        skipUntilString("Event: Event 0 of type RESTRICTION happened.")
        assertNextLine("Task: Task 0 of type COLLECT with ship 1 is added with destination 66.")
    }
}
