package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorpIDDuplicate : ExampleSystemTestExtension() {
    override val description = "Corp ID Duplicate"
    override val corporations = "invalidAssets/corpIDDuplicate.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CorpIDDuplicate"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpIDDuplicate.json is invalid.")
    }
}
