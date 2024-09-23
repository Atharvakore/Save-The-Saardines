package de.unisaarland.cs.se.selab.systemtest.simulationtests.unloading

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests unloading chemicals
 * */
class UnloadingChemicalsTest : ExampleSystemTestExtension() {
    override val description = "tests unloading chemicals"
    override val corporations = "unloadingJsons/unloadingCorporation.json"
    override val scenario = "unloadingJsons/unloadingScenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "UnloadingChemicalsTest"
    override val maxTicks = 2

    override suspend fun run() {
        skipUntilString("Simulation Info: Tick 1 started.")
        skipUntilString("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Unload: Ship 5 unloaded 1000 of garbage CHEMICALS at harbor 59.")
    }
}
