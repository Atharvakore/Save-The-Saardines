package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorporationWhereShip : ExampleSystemTestExtension() {
    override val description = "two tasks with same ID"
    override val corporations = "corporationJsons/corporationWhereShip.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap.json"
    override val name = "ExampleTest"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporationWhereShip.json is invalid.")
    }
}
