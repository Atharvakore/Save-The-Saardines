package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests general garabge collecting
 * */
class StormInfoTest : ExampleSystemTestExtension() {
    override val description = "tests information of a storm event"
    override val corporations = "stormInfoTestJsons/corporation.json"
    override val scenario = "stormInfoTestJsons/scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "StormInfoTest"
    override val maxTicks = 6

    override suspend fun run() {
        assertNextLine("Initialization Info: map_medium_01.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
    }
}
