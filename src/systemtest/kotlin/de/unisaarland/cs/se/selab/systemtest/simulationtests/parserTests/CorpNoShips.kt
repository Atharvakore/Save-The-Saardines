package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorpNoShips : ExampleSystemTestExtension() {
    override val description = "no ships for corp"
    override val corporations = "corporationJsons/corpNoShips.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap.json"
    override val name = "corporation with 0 ships"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpNoShips.json is invalid.")
    }
}
