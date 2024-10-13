package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidContainerCapacity : ExampleSystemTestExtension() {
    override val description = "tests container's capacity"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/invalidCapacityContainer.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "container capacity"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: invalidCapacityContainer.json is invalid.")
    }
}
