package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class MapWithNoGarbage : ExampleSystemTestExtension() {
    override val description = "A map with no garbage should still be valid"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/noGarbage.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "Map With No garbage"
    override val maxTicks = 3
    override suspend fun run() {
        skipLines(2)
        assertNextLine(
            "Initialization Info: " +
                "noGarbage.json successfully parsed and validated."
        )
    }
}
