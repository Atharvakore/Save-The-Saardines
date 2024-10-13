package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Check if it will accept incorrect event Ids*/
class IncorrectEventIds : ExampleSystemTestExtension() {
    override val corporations = "corporationJsons/corporationCorrect.json"
    override val description = "Test for equal ids"
    override val map = "mapFiles/map.json"
    override val maxTicks = 0
    override val name = "Incorrect Event Ids"
    override val scenario = "scenarioJsons/eventIncorrectIds.json"
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporationCorrect.json successfully parsed and validated.")
        assertNextLine("Initialization Info: eventIncorrectIds.json is invalid.")
        assertEnd()
    }
}
