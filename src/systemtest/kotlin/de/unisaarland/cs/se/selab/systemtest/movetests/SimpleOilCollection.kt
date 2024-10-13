package de.unisaarland.cs.se.selab.systemtest.movetests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * Read the description
 */
class SimpleOilCollection : ExampleSystemTestExtension() {
    override val description = "tests the garbage pick up of one oil pile"
    override val corporations = "corporationJsons/simpleOilCorporations.json"
    override val scenario = "scenarioJsons/simpleOilScenario.json"
    override val map = "mapFiles/elevenTileMap.json"
    override val name = "simple oil collection"
    override val maxTicks = 1

    override suspend fun run() {
        assertNextLine("Initialization Info: elevenTileMap.json successfully parsed and validated.")
        assertNextLine("Initialization Info: simpleOilCorporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: simpleOilScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 15 to tile 5.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 5.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 2 collected 1000 of garbage OIL with 1.")
    }
}
