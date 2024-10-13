package de.unisaarland.cs.se.selab.systemtest.movetests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class SimplePlasticLoadUnload : ExampleSystemTestExtension() {
    override val description = "tests fully loading and unloading a collecting ship"
    override val corporations = "corporationJsons/simplePlasticLoadUnloadCorporations.json"
    override val scenario = "scenarioJsons/simplePlasticLoadUnloadScenario.json"
    override val map = "mapFiles/elevenTileMap.json"
    override val name = "simple plastic collection + unloading"
    override val maxTicks = 3

    override suspend fun run() {
        assertNextLine("Initialization Info: elevenTileMap.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: simplePlasticLoadUnloadCorporations.json" +
                " successfully parsed and validated."
        )
        assertNextLine("Initialization Info: simplePlasticLoadUnloadScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 1 collected 1000 of garbage PLASTIC with 1.")
        skipLines(5)
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 4.")
        skipLines(9)
        assertNextLine("Unload: Ship 1 unloaded 1000 of garbage PLASTIC at harbor 4.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Simulation Info: Simulation ended.")
    }
}
