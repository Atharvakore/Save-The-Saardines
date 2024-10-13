package de.unisaarland.cs.se.selab.systemtest.eventTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class OverlapRestrictionTest : ExampleSystemTestExtension() {
    override val description = "Tests overlapping restrictions"
    override val corporations = "corporationJsons/overlappingRestrictionCorpos.json"
    override val scenario = "scenarioJsons/overlappingRestrictionScenario.json"
    override val map = "mapFiles/mediumMapNoCurrents.json"
    override val name = "OverlapRestriction2"
    override val maxTicks = 3

    override suspend fun run() {
        assertNextLine("Initialization Info: mediumMapNoCurrents.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: overlappingRestrictionCorpos.json" +
                " successfully parsed and validated."
        )

        assertNextLine("Initialization Info: overlappingRestrictionScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
    }
}
