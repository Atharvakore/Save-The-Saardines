package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class CurrentWithoutDirection : ExampleSystemTestExtension() {
    override val description = "current must have direction"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/currentLackDirection.json"
    override val name = "CurrentWithoutDirection"
    override val maxTicks = 0

    override suspend fun run() {
        assertNextLine("Initialization Info: currentLackDirection.json is invalid.")
    }
}
