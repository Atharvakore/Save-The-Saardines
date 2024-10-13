package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class LandMeetShallow : ExampleSystemTestExtension() {
    override val description = "land tile can not have shallow as neighbor"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/landMeetShallowMap.json"
    override val name = "LandMeetShallow"
    override val maxTicks = 0

    override suspend fun run() {
        assertNextLine("Initialization Info: landMeetShallowMap.json is invalid.")
    }
}
