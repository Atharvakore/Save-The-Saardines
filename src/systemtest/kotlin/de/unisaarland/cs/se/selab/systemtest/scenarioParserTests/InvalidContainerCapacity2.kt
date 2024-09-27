package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidContainerCapacity2 : ExampleSystemTestExtension() {
    override val description = "tests container's capacity upper bounds"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidCapacityContainer2.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "container capacity"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidCapacityContainer2.json is invalid.")
    }
}
