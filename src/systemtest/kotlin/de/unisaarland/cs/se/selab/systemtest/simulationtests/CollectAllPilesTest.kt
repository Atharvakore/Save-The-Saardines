package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests general garabge collecting
 * */
class CollectAllPilesTest : ExampleSystemTestExtension() {
    override val description = "test collect all piles"
    override val corporations = "collectAllPilesTestJsons/corporation.json"
    override val scenario = "collectAllPilesTestJsons/scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CollectAllPilesTest"
    override val maxTicks = 2

    override suspend fun run() {
        assertNextLine("Initialization Info: map_medium_01.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
    }
}
