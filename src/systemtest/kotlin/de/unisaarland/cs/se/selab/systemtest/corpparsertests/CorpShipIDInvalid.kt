package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorpShipIDInvalid : ExampleSystemTestExtension() {
    override val description = "Corp has invalid ship"
    override val corporations = "invalidAssets/corpShipIDInvalid.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CorpShipIDInvalid"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpShipIDInvalid.json is invalid.")
    }
}
