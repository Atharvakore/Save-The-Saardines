package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class CurrentWithoutSpeed : ExampleSystemTestExtension() {
    override val description = "current must have speed"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/currentLackSpeed.json"
    override val name = "CurrentWithoutSpeed"
    override val maxTicks = 0

    override suspend fun run() {
        assertNextLine("Initialization Info: currentLackSpeed.json is invalid.")
    }
}
