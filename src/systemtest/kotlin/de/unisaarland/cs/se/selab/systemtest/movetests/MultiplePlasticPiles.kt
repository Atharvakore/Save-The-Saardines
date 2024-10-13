package de.unisaarland.cs.se.selab.systemtest.movetests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class MultiplePlasticPiles : ExampleSystemTestExtension() {
    override val description = "tests picking up "
    override val corporations = "corporationJsons/simplePlasticLoadUnloadCorporations.json"
    override val scenario = "scenarioJsons/multiplePlasticPilesScenario.json"
    override val map = "mapFiles/elevenTileMap.json"
    override val name = "correct order of pick up of multiple piles on a single tile"
    override val maxTicks = 1

    override suspend fun run() {
        assertNextLine("Initialization Info: elevenTileMap.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: simplePlasticLoadUnloadCorporations.json" +
                " successfully parsed and validated."
        )
        assertNextLine("Initialization Info: multiplePlasticPilesScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 1 collected 500 of garbage PLASTIC with 1.")
        assertNextLine("Garbage Collection: Ship 1 collected 200 of garbage PLASTIC with 2.")
        assertNextLine("Garbage Collection: Ship 1 collected 300 of garbage PLASTIC with 3.")
    }
}
