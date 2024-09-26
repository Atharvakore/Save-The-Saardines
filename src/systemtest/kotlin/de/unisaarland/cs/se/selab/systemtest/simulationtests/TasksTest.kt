package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * Tests Tasks
 */
class TasksTest : ExampleSystemTestExtension() {
    override val description = "tests Task Assignment and Working"
    override val corporations = "corporationJsons/stormEventCorporation.json"
    override val scenario = "scenarioJsons/TaskTest.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "TaskTest"
    override val maxTicks = 25

    override suspend fun run() {
        assertNextLine("Simulation Info: Tick 0 started.")
    }
}
