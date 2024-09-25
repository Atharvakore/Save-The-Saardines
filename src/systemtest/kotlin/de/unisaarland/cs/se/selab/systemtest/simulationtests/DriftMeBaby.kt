package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**  Drift garbage and ship to multiple tiles*/
class DriftMeBaby : ExampleSystemTestExtension() {
    override val description = "Test drifting of garbage and ships to multiple tiles"
    override val corporations = "driftAll/corporation.json"
    override val scenario = "driftAll/scenario.json"
    override val map = "driftAll/map.json"
    override val name = "Drift Me Baby"
    override val maxTicks = 4
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Current Drift: OIL 5 with amount 150 drifted from tile 9 to tile 1.")
        assertNextLine("Current Drift: Ship 1 drifted from tile 9 to tile 1.")
        assertNextLine("Simulation Info: Tick 1 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 1 collected 5000 of garbage CHEMICALS with 2.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        skipLines(2)
        assertNextLine("Current Drift: OIL 1 with amount 50 drifted from tile 9 to tile 6.")
        assertNextLine("Current Drift: PLASTIC 6 with amount 100 drifted from tile 9 to tile 1.")
    }
}
