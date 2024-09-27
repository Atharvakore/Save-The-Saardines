package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidTaskCooperate : ExampleSystemTestExtension() {
    override val description = "invalid cooperate task that target tile must have one harbor of other corporation"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidTaskCooperate.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "invalid cooperate task"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidTaskCooperate.json is invalid.")
    }
}
