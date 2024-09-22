package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Check if Garbages of Corporation equals to Ships */
class IncorrectShipsGarbages : ExampleSystemTestExtension() {
    override val corporations = "corporationJsons/corporationIncorrectShipGarbage.json"
    override val description = "Test Incorrect Ships with garbage"
    override val map = "mapFiles/map.json"
    override val maxTicks = 0
    override val name = "Incorrect Garbage on Ships"
    override val scenario = "scenarioJsons/scenario1.json"
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporationIncorrectShipGarbage.json is invalid.")
        assertEnd()
    }
}
