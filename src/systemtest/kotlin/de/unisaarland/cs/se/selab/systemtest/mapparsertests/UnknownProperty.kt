package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class UnknownProperty : ExampleSystemTestExtension() {
    override val description = "unknown property like 'abc' exists in file"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/invalidProperty.json"
    override val name = "UnknownProperty"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: invalidProperty.json is invalid.")
    }
}
