package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class LandHasCurrent : ExampleSystemTestExtension() {
    override val description = "land tile can not have current"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/smallMapLandCurrent.json"
    override val name = "LandHasCurrent"
    override val maxTicks = 0

    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapLandCurrent.json is invalid.")
    }
}
