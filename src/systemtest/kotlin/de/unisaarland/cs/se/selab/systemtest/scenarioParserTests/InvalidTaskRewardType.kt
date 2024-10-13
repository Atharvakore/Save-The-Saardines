package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidTaskRewardType : ExampleSystemTestExtension() {
    override val description = "reward type that can't match the tasks"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidTaskRewardType.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "invalid task reward type"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidTaskRewardType.json is invalid.")
    }
}
