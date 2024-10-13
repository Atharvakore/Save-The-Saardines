package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidExploreRewardShip : ExampleSystemTestExtension() {
    override val description = "Explore tasks with invalid Reward shipID"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidExploreRewardShipID.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "Explore task invalid Reward shipID"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidExploreRewardShipID.json is invalid.")
    }
}
