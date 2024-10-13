package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class LandHasHarbor : ExampleSystemTestExtension() {
    override val description = "land tile can not have harbor"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/smallMapLandHarbor.json"
    override val name = "LandHasHarbor"
    override val maxTicks = 0

    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapLandHarbor.json is invalid.")
    }
}
