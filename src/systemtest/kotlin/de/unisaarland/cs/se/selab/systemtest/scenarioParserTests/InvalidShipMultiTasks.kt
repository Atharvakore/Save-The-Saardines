package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidShipMultiTasks : ExampleSystemTestExtension() {
    override val description = "ships has multi tasks at the same tick"
    override val corporations = "corporationJsons/TaskDifferentCorp.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidShipTasks.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "invalid ship multi tasks"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: TaskDifferentCorp.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidShipTasks.json is invalid.")
    }
}
