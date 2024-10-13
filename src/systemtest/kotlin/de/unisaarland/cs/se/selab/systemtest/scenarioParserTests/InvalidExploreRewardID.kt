package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidExploreRewardID : ExampleSystemTestExtension() {
    override val description = "Explore tasks with invalid Reward ID"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidExploreRewardID.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "Explore task invalid Reward ID"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidExploreRewardID.json is invalid.")
    }
}
