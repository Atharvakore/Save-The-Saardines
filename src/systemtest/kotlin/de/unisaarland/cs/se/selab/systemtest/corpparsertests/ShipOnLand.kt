package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class ShipOnLand : ExampleSystemTestExtension() {
    override val description = "invalid ship on the land tile"
    override val corporations = "invalidAssets/shipOnLand.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMapLand.json"
    override val name = "ShipOnLand"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapLand.json successfully parsed and validated.")
        assertNextLine("Initialization Info: shipOnLand.json is invalid.")
    }
}
