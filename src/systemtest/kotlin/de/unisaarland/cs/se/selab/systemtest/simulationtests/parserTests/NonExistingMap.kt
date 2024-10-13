package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class NonExistingMap : ExampleSystemTestExtension() {
    override val description = "two tasks with same ID"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "Try404/scenario.json"
    override val map = "mapFiles/dummyMap.json"
    override val name = "ExampleTest"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: dummyMap.json is invalid")
    }
}
