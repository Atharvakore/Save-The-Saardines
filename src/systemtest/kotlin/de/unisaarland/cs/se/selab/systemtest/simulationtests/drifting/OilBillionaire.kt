package de.unisaarland.cs.se.selab.systemtest.simulationtests.drifting

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * Drift Too much OIL
 */
class OilBillionaire : ExampleSystemTestExtension() {
    override val description = "Test drifting of garbage To Ship Tile"
    override val corporations = "driftAll/corporationOilBillionaire.json"
    override val scenario = "driftAll/scenarioOilBillionaire.json"
    override val map = "driftAll/mapOilBillionaire.json"
    override val name = "Oil Billionaire"
    override val maxTicks = 5
    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to refuel.")
        skipLines(1)
        assertNextLine("Current Drift: OIL 6 with amount 300 drifted from tile 9 to tile 9.")
        skipUntilString("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Current Drift: OIL 7 with amount 300 drifted from tile 9 to tile 9.")

        skipUntilString("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Current Drift: OIL 1 with amount 200 drifted from tile 9 to tile 1.")
        assertNextLine("Current Drift: PLASTIC 8 with amount 100 drifted from tile 9 to tile 1.")
    }
}
