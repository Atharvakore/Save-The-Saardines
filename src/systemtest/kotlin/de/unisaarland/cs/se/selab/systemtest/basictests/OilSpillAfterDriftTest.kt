package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class OilSpillAfterDriftTest : ExampleSystemTestExtension() {
    override val description = "test oil spill event after drift the garbage"
    override val corporations = "corporationJsons/SimpleEventCorp.json"
    override val scenario = "scenarioJsons/EventTestScenario.json"
    override val map = "mapFiles/TestMap.json"
    override val name = "Oil Spill After Drift test"
    override val maxTicks = 1

    override suspend fun run() {
        assertNextLine("Initialization Info: TestMap.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: SimpleEventCorp.json successfully parsed and validated."
        )
        assertNextLine("Initialization Info: EventTestScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        skipLines(7)
        assertNextLine("Current Drift: OIL 5 with amount 100 drifted from tile 17 to tile 3.")
        assertNextLine("Current Drift: OIL 6 with amount 50 drifted from tile 22 to tile 24.")
        assertNextLine("Current Drift: OIL 7 with amount 100 drifted from tile 32 to tile 8.")
        assertNextLine("Event: Event 1 of type OIL_SPILL happened.")
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 4000.")
    }
}
