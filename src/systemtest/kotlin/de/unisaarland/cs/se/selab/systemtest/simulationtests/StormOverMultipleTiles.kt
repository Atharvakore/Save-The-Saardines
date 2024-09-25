package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTestAssertionError
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import de.unisaarland.cs.se.selab.systemtest.utils.Logs

/** Simulates Storm Event over multiple Tiles*/
class StormOverMultipleTiles : ExampleSystemTestExtension() {

    override val description = "tests storm event over multiple tiles"
    override val corporations = "stormMultipleTilesJsons/corporation.json"
    override val scenario = "stormMultipleTilesJsons/scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "StormOverMultipleTiles"
    override val maxTicks = 1

    override suspend fun run() {
        assertLine("Initialization Info: map_medium_01.json successfully parsed and validated.", true)
        assertNextLine("Initialization Info: corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Current Drift: PLASTIC 3 with amount 50 drifted from tile 64 to tile 65.")
        assertNextLine("Current Drift: OIL 4 with amount 50 drifted from tile 65 to tile 66.")
        assertNextLine("Current Drift: Ship 1 drifted from tile 54 to tile 44.")
        assertNextLine("Event: Event 1 of type STORM happened.")
        assertNextLine("Simulation Info: Simulation ended.")
    }
}
