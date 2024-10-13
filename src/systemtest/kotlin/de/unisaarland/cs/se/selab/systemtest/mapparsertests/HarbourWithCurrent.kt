package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class HarbourWithCurrent : ExampleSystemTestExtension() {
    override val description = "A harbour tile on the map shouldn't have a current"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/smallMapHarbourWithCurrent.json"
    override val name = "HarbourWithCurrentMap"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapHarbourWithCurrent.json is invalid.")
    }
}
