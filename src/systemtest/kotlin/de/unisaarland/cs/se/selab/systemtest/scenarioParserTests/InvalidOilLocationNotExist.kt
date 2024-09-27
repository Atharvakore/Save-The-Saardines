package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidOilLocationNotExist : ExampleSystemTestExtension() {
    override val description = "invalid Oil location that on not exist tile"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidStormLocationNotExist.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "invalid Oil location that on not exist tile"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidStormLocationNotExist.json is invalid.")
    }
}
