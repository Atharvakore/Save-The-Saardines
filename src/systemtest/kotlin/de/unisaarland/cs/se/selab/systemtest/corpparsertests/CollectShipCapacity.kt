package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CollectShipCapacity : ExampleSystemTestExtension() {
    override val description = "collect ship capacity for plastic upper bounds"
    override val corporations = "invalidAssets/collectshipCapacityPlastic.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CollectShipCapacity"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: collectshipCapacityPlastic.json is invalid.")
    }
}
