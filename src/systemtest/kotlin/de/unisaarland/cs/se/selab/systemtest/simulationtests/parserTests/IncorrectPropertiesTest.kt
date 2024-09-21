package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
/** Test for small map*/
class IncorrectPropertiesTest : ExampleSystemTestExtension() {
    override val corporations = "corporationJsons/corporations1.json"
    override val description = "Test if the map has only required properties"
    override val map = "mapFiles/map.json"
    override val maxTicks = 0
    override val name = "IncorrectPropertiesTest"
    override val scenario = "scenarioJsons/scenario1.json"
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations1.json is invalid.")
        assertEnd()
    }
}
