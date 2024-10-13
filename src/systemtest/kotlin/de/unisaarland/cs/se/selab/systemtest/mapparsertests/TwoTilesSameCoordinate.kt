package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class TwoTilesSameCoordinate : ExampleSystemTestExtension() {
    override val description = "two tiles should not have same coordinate"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/smallMapSameCoordinate.json"
    override val name = "TwoTilesSameCoordinate"
    override val maxTicks = 0

    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapSameCoordinate.json is invalid.")
    }
}
