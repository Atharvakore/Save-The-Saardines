package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class LandWithDeep : ExampleSystemTestExtension() {
    override val description = "A land behinds deep"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/smallMapInvalidLand.json"
    override val name = "LandWithDeep"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapInvalidLand.json is invalid.")
    }
}
