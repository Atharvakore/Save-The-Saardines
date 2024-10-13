package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorpIDNegative : ExampleSystemTestExtension() {
    override val description = "Corp ID negative"
    override val corporations = "invalidAssets/corpIDNegative.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CorpIDNegative"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpIDNegative.json is invalid.")
    }
}
