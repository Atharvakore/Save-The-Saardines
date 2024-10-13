package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidOilAmountNegative : ExampleSystemTestExtension() {
    override val description = "invalid Oil Amount which is negative"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/oilAmountNegative.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "invalid oil amount which is negative"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: oilAmountNegative.json is invalid.")
    }
}
