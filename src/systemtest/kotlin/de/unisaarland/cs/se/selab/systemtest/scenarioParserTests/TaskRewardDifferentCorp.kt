package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class TaskRewardDifferentCorp : ExampleSystemTestExtension() {
    override val description = "tasks and rewards with different corporations"
    override val corporations = "invalidAssets/TaskDifferentCorp.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidShipCorp.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "TaskRewardDifferentCorp"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: TaskDifferentCorp.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidShipCorp.json is invalid.")
    }
}
