package de.unisaarland.cs.se.selab.systemtest.eventTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class StormOilTargetTest : ExampleSystemTestExtension() {
    override val description = "tests if piles get drifted onto the right tiles by a storm"
    override val corporations = "corporationJsons/stormDriftTargetCorporations.json"
    override val scenario = "scenarioJsons/stormDriftScenarion.json"
    override val map = "mapFiles/elevenTileMap.json"
    override val name = "Storm Drift Test"
    override val maxTicks = 2

    override suspend fun run() {
        assertNextLine("Initialization Info: elevenTileMap.json successfully parsed and validated.")
        assertNextLine("Initialization Info: stormDriftTargetCorporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: stormDriftScenarion.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        skipLines(4)
        assertNextLine("Event: Event 1 of type STORM happened.")
        skipLines(3)
        assertNextLine("Garbage Collection: Ship 1 collected 500 of garbage OIL with 1.")
    }
}
