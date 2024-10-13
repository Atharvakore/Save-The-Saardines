package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidRewardIDDuplicate : ExampleSystemTestExtension() {
    override val description = "reward ID that is duplicated"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidRewardIDDuplicate.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "reward ID duplicated"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidRewardIDDuplicate.json is invalid.")
    }
}
