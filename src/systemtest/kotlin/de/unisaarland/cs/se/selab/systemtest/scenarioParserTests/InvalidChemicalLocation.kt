package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidChemicalLocation : ExampleSystemTestExtension() {
    override val description = "invalid chemical location at the deep ocean Tile"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/chemicalLocationInvalid.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "invalid chemical location deep ocean Tile"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: chemicalLocationInvalid.json is invalid.")
    }
}
