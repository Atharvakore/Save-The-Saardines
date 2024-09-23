package de.unisaarland.cs.se.selab.systemtest.simulationtests.unloading

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests unloading plastic
 * */
class UnloadingPlasticTest : ExampleSystemTestExtension() {
    override val description = "tests unloading plastic"
    override val corporations = "unloadingJsons/unloadingCorporation.json"
    override val scenario = "unloadingJsons/unloadingScenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "UnloadingPlasticTest"
    override val maxTicks = 2

    override suspend fun run() {
        skipUntilString("Simulation Info: Tick 1 started.")
        skipUntilString("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Unload: Ship 6 unloaded 1000 of garbage PLASTIC at harbor 14.")
    }
}
