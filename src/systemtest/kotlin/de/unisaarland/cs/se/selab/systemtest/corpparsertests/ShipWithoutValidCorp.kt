package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class ShipWithoutValidCorp : ExampleSystemTestExtension() {
    override val description = "Ship does not have a corp"
    override val corporations = "corporationJsons/shipWithoutCorp.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "ShipWithoutCorp"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: shipWithoutCorp.json is invalid.")
    }
}
