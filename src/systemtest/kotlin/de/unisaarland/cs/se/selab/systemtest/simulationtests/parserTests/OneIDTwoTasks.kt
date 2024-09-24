package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class OneIDTwoTasks : ExampleSystemTestExtension() {
    override val description = "two tasks with same ID"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenarioOneIDTwoTasks.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "oneIdTwoTasks"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenarioOneIDTwoTasks.json is invalid.")
    }
}
