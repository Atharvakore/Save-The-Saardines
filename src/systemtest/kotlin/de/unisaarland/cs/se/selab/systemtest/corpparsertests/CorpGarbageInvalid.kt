package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorpGarbageInvalid : ExampleSystemTestExtension() {
    override val description = "Corp has invalid garbage type"
    override val corporations = "invalidAssets/corpGarbageInvalid.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CorpGarbageInvalid"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpGarbageInvalid.json is invalid.")
    }
}
