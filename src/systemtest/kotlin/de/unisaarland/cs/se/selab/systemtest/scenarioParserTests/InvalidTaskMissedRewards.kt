package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidTaskMissedRewards : ExampleSystemTestExtension() {
    override val description = "Missed rewards for task"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidTaskMissedRewards.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "Missed rewards for task"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidTaskMissedRewards.json is invalid.")
    }
}
