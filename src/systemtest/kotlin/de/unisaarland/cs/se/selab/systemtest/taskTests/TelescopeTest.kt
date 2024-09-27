package de.unisaarland.cs.se.selab.systemtest.taskTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class TelescopeTest : ExampleSystemTestExtension() {
    override val description = "tests if a ship which gets a telescope moves to garbage now"
    override val corporations = "corporationJsons/telescopeTestCorporations.json"
    override val scenario = "scenarioJsons/telescopeTestScenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "Telescope Test"
    override val maxTicks = 7
    override suspend fun run() {
        assertNextLine("Initialization Info: map_medium_01.json successfully parsed and validated.")
        assertNextLine("Initialization Info: telescopeTestCorporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: telescopeTestScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
    }
}
