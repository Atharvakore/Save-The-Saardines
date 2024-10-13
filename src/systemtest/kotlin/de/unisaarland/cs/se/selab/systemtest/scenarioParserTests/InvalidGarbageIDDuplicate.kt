package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidGarbageIDDuplicate : ExampleSystemTestExtension() {
    override val description = "invalid garbage ID that is duplicated"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/duplicateGarbageID.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "garbage ID that is duplicated"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: duplicateGarbageID.json is invalid.")
    }
}
