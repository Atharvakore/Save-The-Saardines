package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTestAssertionError
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import de.unisaarland.cs.se.selab.systemtest.utils.Logs

/**
 * example system test
 */
class OneIDTwoTasks : ExampleSystemTestExtension() {
    override val description = "two tasks with same ID"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenarioOneIDTwoTasks.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "ExampleTest"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenarioOneIDTwoTasks.json is invalid.")

    }
}
