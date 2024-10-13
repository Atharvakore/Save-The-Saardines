package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidStormSpeed : ExampleSystemTestExtension() {
    override val description = "invalid speed for storm that exceeds 30"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidStormSpeed.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "invalid speed for storm"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidStormSpeed.json is invalid.")
    }
}
