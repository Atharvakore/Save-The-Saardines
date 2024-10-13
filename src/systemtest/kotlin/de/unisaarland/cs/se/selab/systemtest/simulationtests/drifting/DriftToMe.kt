package de.unisaarland.cs.se.selab.systemtest.simulationtests.drifting

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
/** */
class DriftToMe : ExampleSystemTestExtension() {
    override val description = "Test drifting of garbage To Ship Tile"
    override val corporations = "driftAll/corporationDriftToMe.json"
    override val scenario = "driftAll/scenarioDriftToMe.json"
    override val map = "driftAll/map.json"
    override val name = "DriftToMe"
    override val maxTicks = 5
    override suspend fun run() {
        skipUntilString("Simulation Info: Tick 1 started.")
        skipUntilString("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 1 collected 150 of garbage OIL with 4.")
        skipUntilString("Current Drift: OIL 1 with amount 50 drifted from tile 9 to tile 1.")
        skipUntilString("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 1 collected 50 of garbage OIL with 1.")
    }
}
