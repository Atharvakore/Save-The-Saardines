package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidGarbageID : ExampleSystemTestExtension() {
    override val description = "invalid negative garbage ID"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidGarbageID.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "invalid negative garbage ID"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidGarbageID.json is invalid.")
    }
}
