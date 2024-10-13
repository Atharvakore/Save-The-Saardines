package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class CurrentWithoutIntensity : ExampleSystemTestExtension() {
    override val description = "current must have intensity"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/currentLackIntensity.json"
    override val name = "CurrentWithoutIntensity"
    override val maxTicks = 0

    override suspend fun run() {
        assertNextLine("Initialization Info: currentLackIntensity.json is invalid.")
    }
}
