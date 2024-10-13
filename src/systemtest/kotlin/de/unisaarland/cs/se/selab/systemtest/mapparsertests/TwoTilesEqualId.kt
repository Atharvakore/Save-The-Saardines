package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class TwoTilesEqualId : ExampleSystemTestExtension() {
    override val description = "two tiles should not have same id"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMapsameid.json"
    override val name = "TwoTilesEqualId"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapsameid.json is invalid.")
    }
}
