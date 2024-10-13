package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Test for ships with equal id*/
class IncorrectShipsIds : ExampleSystemTestExtension() {
    override val corporations = "corporationJsons/corporationIncorrectShipIds.json"
    override val description = "Test ships with equal id"
    override val map = "mapFiles/map.json"
    override val maxTicks = 0
    override val name = "Incorrect Properties Ship Id"
    override val scenario = "scenarioJsons/scenario1.json"
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporationIncorrectShipIds.json is invalid.")
        assertEnd()
    }
}
