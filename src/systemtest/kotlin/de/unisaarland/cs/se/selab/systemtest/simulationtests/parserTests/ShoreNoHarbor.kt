package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class ShoreNoHarbor : ExampleSystemTestExtension() {
    override val description = "two tasks with same ID"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenarioOneIDTwoTasks.json"
    override val map = "mapFiles/shoreNoHarbor.json"
    override val name = "ExampleTest"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: shoreNoHarbor.json is invalid.")
    }
}
