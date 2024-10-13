package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class SpeedAboveRange : ExampleSystemTestExtension() {
    override val description = "speed should be in [10, 30]"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/bigSpeed.json"
    override val name = "SpeedAboveRange"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: bigSpeed.json is invalid.")
    }
}
