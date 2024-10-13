package de.unisaarland.cs.se.selab.systemtest.eventTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class LeavingRestrictionTest : ExampleSystemTestExtension() {
    override val description = "tests leaving a restricted area "
    override val corporations = "corporationJsons/leavingRestrictionTest.json"
    override val scenario = "scenarioJsons/leavingRestrictionScenario.json"
    override val map = "mapFiles/elevenTileMap.json"
    override val name = "tests if the ships leave or remain in the restriction"
    override val maxTicks = 4

    override suspend fun run() {
        assertNextLine("Initialization Info: elevenTileMap.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: leavingRestrictionTest.json" +
                " successfully parsed and validated."
        )

        assertNextLine("Initialization Info: leavingRestrictionScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
    }
}
