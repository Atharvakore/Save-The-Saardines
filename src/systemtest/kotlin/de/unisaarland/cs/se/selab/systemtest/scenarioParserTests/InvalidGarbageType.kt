package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidGarbageType : ExampleSystemTestExtension() {
    override val description = "invalid garbage Type"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidGarbageType.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "invalid garbage Type"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidGarbageType.json is invalid.")
    }
}
