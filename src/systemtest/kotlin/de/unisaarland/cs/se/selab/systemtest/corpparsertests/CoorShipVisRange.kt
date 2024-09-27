package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CoorShipVisRange : ExampleSystemTestExtension() {
    override val description = "coordinating ship visibility range"
    override val corporations = "invalidAssets/coorVisRange.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CollectShipCapacity"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: coorVisRange.json is invalid.")
    }
}
