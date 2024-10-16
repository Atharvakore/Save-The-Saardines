package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test the tick25 sequence from spec
 * */
class SequenceTick25Test : ExampleSystemTestExtension() {
    override val description = "tests tick25 sequence from spec"
    override val corporations = "corporationJsons/tick25Corporation.json"
    override val scenario = "scenarioJsons/tick25Scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "SequenceTick25Test"
    override val maxTicks = 1
    override suspend fun run() {
        assertLine("Initialization Info: map_medium_01.json successfully parsed and validated.", true)
        assertNextLine("Initialization Info: tick25Corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: tick25Scenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 66.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 34.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Current Drift: OIL 1 with amount 50 drifted from tile 66 to tile 67.")
        assertNextLine("Current Drift: Ship 1 drifted from tile 66 to tile 67.")
        assertNextLine("Current Drift: Ship 2 drifted from tile 34 to tile 24.")
        assertNextLine("Simulation Info: Simulation ended.")

        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 800.")
    }
}
