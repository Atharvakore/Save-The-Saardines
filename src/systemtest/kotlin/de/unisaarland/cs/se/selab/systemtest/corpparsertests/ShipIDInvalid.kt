package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class ShipIDInvalid : ExampleSystemTestExtension() {
    override val description = "invalid shipID"
    override val corporations = "invalidAssets/shipIDInvalid.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "ShipIdInvalid"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: shipIDInvalid.json is invalid.")
    }
}
