package de.unisaarland.cs.se.selab.systemtest.movetests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class TestCollectUnload : ExampleSystemTestExtension() {
    override val description = "tests ship with container will unload"
    override val corporations = "corporationJsons/exploreSingleScout.json"
    override val scenario = "scenarioJsons/exploreTest.json"
    override val map = "mapFiles/elevenTileMap.json"
    override val name = "default explore"
    override val maxTicks = 3

    override suspend fun run() {
        assertNextLine("Initialization Info: elevenTileMap.json successfully parsed and validated.")
        assertNextLine("Initialization Info: exploreSingleScout.json successfully parsed and validated.")
        assertNextLine("Initialization Info: exploreTest.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 5.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 2.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 1 moved with speed 30 to tile 4.")
    }
}
