package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class SampleSimulationTest : ExampleSystemTestExtension() {
    override val description = "tests statistics after 0 ticks"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "ExampleTest"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Simulation start")
    }
}
