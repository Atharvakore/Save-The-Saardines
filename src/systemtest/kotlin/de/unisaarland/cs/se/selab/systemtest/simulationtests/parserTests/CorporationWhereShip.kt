package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorporationWhereShip : ExampleSystemTestExtension() {
    override val description = "corporation with non-existing ships"
    override val corporations = "corporationJsons/corporationWhereShip.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap.json"
    override val name = "corporationWhereShip"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporationWhereShip.json is invalid.")
    }
}
