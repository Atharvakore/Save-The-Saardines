package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class IntensityAboveRange : ExampleSystemTestExtension() {
    override val description = "intensity should be in [1, 10]"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/bigIntensity.json"
    override val name = "IntensityAboveRange"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: bigIntensity.json is invalid.")
    }
}
