package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Check if Corporation has no GarbageTypes and no collectingShip */
class ShipAmongUs : ExampleSystemTestExtension() {
    override val corporations = "corporationJsons/corporationIncorrectGarbage.json"
    override val description = "Test if corporation has no garbage type and no collecting ship"
    override val map = "mapFiles/map.json"
    override val maxTicks = 0
    override val name = "GarbageShipAmongUs"
    override val scenario = "scenarioJsons/scenario1.json"
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporationIncorrectGarbage.json is invalid.")
        assertEnd()
    }
}
