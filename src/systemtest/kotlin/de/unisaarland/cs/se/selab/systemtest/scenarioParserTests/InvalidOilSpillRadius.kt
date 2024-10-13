package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidOilSpillRadius : ExampleSystemTestExtension() {
    override val description = "invalid Oil Spill radius that negative"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidOilSpillRadius.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "invalid Oil Spill radius negative"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidOilSpillRadius.json is invalid.")
    }
}
