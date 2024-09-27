package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidExploreShip : ExampleSystemTestExtension() {
    override val description = "Explore tasks with invalid shipID"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidExploreShipID.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "Explore task invalid shipID"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidExploreShipID.json is invalid.")
    }
}
