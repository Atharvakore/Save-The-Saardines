package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class DirectionNotRight : ExampleSystemTestExtension() {
    override val description = "direction can only be some specific values"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/invalidDirection.json"
    override val name = "DirectionNotRight"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: invalidDirection.json is invalid.")
    }
}
