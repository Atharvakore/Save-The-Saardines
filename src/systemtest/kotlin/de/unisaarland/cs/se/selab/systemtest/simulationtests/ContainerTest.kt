package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test for container
 */
class ContainerTest : ExampleSystemTestExtension() {
    override val description = "tests for containers"
    override val corporations = "containerTestJsons/corporation.json"
    override val scenario = "containerTestJsons/scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "ContainerTest"
    override val maxTicks = 8

    override suspend fun run() {
        assertNextLine("Initialization Info: map_medium_01.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
    }
}
