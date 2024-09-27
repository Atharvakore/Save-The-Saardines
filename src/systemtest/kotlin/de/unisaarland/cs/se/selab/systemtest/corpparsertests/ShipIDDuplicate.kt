package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class ShipIDDuplicate : ExampleSystemTestExtension() {
    override val description = "invalid shipID duplicate"
    override val corporations = "invalidAssets/shipIDDuplicate.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "ShipIdDuplicate"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: shipIDDuplicate.json is invalid.")
    }
}
