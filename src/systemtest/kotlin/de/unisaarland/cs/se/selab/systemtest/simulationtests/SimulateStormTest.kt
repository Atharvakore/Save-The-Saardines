package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTestAssertionError
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import de.unisaarland.cs.se.selab.systemtest.utils.Logs

/** Simulates Storm Event over multiple Tiles*/
class SimulateStormTest : ExampleSystemTestExtension() {

    override val description = "tests storm event over multiple events"
    override val corporations = "corporationJsons/stormEventCorporation.json"
    override val scenario = "scenarioJsons/stormEventTest.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "StormEventTest"
    override val maxTicks = 1

    override suspend fun run() {
        val expectedString = "Simulation Info: Simulation started."
        if (skipUntilLogType(Logs.SIMULATION_INFO) != expectedString) {
            throw SystemTestAssertionError("Collected plastic should be 0!")
        }
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Current Drift: OIL 2 with amount 50 drifted from tile 67 to tile 68.")
        assertNextLine("Event: Event 1 of type STORM happened.")
        assertNextLine("Simulation Info: Simulation ended.")
    }
}
