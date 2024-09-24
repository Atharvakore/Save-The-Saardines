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
    override val maxTicks = 3

    override suspend fun run() {
        skipUntilString("Simulation Info: Tick 2 started.")
        skipUntilString("Unload: Ship 5 unloaded 1000 of garbage CHEMICALS at harbor 59.")
        assertNextLine("Unload: Ship 6 unloaded 1000 of garbage PLASTIC at harbor 14.")
    }
}
