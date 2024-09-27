package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidChemicalLocationLand : ExampleSystemTestExtension() {
    override val description = "invalid chemical location at the Land Tile"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/chemicalLocationLand.json"
    override val map = "mapFiles/smallMapLand.json"
    override val name = "invalid chemical location at the Land Tile"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapLand.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: chemicalLocationLand.json is invalid.")
    }
}
