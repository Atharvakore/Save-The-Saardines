package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidStormLocation : ExampleSystemTestExtension() {
    override val description = "invalid storm location at Land"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidStormLocation.json"
    override val map = "mapFiles/smallMapLand.json"
    override val name = "invalid storm location at Land"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapLand.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidStormLocation.json is invalid.")
    }
}
