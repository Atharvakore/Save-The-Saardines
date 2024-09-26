package de.unisaarland.cs.se.selab.systemtest.simulationtests.scouting

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Test Exploring and moving to a Oil Event */
class GodVision : ExampleSystemTestExtension() {
    override val description = "Test Exploring and moving to a Oil Event"
    override val corporations = "godVision/corporation.json"
    override val scenario = "godVision/scenario.json"
    override val map = "godVision/map.json"
    override val name = "God Vision"
    override val maxTicks = 10
    override suspend fun run() {
        skipUntilString("Simulation Info: Tick 0 started.")
    }
}
