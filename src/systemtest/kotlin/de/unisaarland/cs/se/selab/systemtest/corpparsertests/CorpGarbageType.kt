package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorpGarbageType : ExampleSystemTestExtension() {
    override val description = "Corp has different garbage types but not have corresponds collect ship"
    override val corporations = "invalidAssets/corpGarbageType.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CorpGarbageType"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpGarbageType.json is invalid.")
    }
}
