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
        assertNextLine("Initialization Info: mapOilBillionaire.json successfully parsed and validated.")
    }
}