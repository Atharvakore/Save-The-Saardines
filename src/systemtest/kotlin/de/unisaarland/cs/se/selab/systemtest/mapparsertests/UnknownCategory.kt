package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class UnknownCategory : ExampleSystemTestExtension() {
    override val description = "unknown category like 'SAAR' exists in file"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/invalidCategory.json"
    override val name = "UnknownCategory"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: invalidCategory.json is invalid.")
    }
}
