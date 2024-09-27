package de.unisaarland.cs.se.selab.systemtest.movetests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class PlasticCollectCheck : ExampleSystemTestExtension() {
    override val description = "tests if a ships only collects garbage when there is enough capacity"
    override val corporations = "corporationJsons/shipGetsDriftedOntoPlasticCorpo.json"
    override val scenario = "scenarioJsons/plasticCollectCheckScenario.json"
    override val map = "mapFiles/elevenTileSmallCurrent.json"
    override val name = "plastic collect check"
    override val maxTicks = 2

    override suspend fun run() {
        assertNextLine("Initialization Info: elevenTileSmallCurrent.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: shipGetsDriftedOntoPlasticCorpo.json" +
                " successfully parsed and validated."
        )
        assertNextLine("Initialization Info: plasticCollectCheckScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 7.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        skipLines(2)
        assertNextLine("Current Drift: Ship 2 drifted from tile 7 to tile 5.")
        skipLines(3)
        assertNextLine("Garbage Collection: Ship 1 collected 500 of garbage PLASTIC with 1.")
        assertNextLine("Garbage Collection: Ship 1 collected 200 of garbage PLASTIC with 2.")
        assertNextLine("Garbage Collection: Ship 1 collected 300 of garbage PLASTIC with 3.")
        assertNextLine("Garbage Collection: Ship 2 collected 600 of garbage PLASTIC with 4.")
        skipLines(3)
        assertNextLine("Simulation Info: Simulation ended.")
    }
}
