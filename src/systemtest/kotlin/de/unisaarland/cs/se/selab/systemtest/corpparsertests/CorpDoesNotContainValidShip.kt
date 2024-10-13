package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorpDoesNotContainValidShip : ExampleSystemTestExtension() {
    override val description = "Ship does not have a corp"
    override val corporations = "corporationJsons/corpDoesNotContainValidShip.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CorpDoesNotContainValidShip"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpDoesNotContainValidShip.json is invalid.")
    }
}
