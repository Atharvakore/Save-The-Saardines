package de.unisaarland.cs.se.selab.systemtest.scenarioTests
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class ScenarioTestMoreGarbage : ExampleSystemTestExtension() {
    override val description = "Corp with more ships in the corporation file"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenarioTestMoreGarbage.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "SimWithoutCorp"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenarioTestMoreGarbage.json successfully parsed and validated.")
    }
}
