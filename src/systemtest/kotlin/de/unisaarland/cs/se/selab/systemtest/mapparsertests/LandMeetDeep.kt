package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class LandMeetDeep : ExampleSystemTestExtension() {
    override val description = "land tile can not have deep ocean as neighbor"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/landMeetDeepMap.json"
    override val name = "LandMeetDeep"
    override val maxTicks = 0

    override suspend fun run() {
        assertNextLine("Initialization Info: landMeetDeepMap.json is invalid.")
    }
}
