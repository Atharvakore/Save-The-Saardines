package de.unisaarland.cs.se.selab.systemtest.eventTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class OilSpillTest : ExampleSystemTestExtension() {
    override val description = "tests oil spill events "
    override val corporations = "corporationJsons/oilSpillTestCorporations.json"
    override val scenario = "scenarioJsons/oilSpillTest.json"
    override val map = "mapFiles/baseMapWithCurrent.json"
    override val name = "tests if spilled piles are picked up by oil tankers"
    override val maxTicks = 2

    override suspend fun run() {
        assertNextLine("Initialization Info: baseMapWithCurrent.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: oilSpillTestCorporations.json" +
                " successfully parsed and validated."
        )

        assertNextLine("Initialization Info: oilSpillTest.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        skipLines(5)
        assertNextLine("Current Drift: Ship 5 drifted from tile 1 to tile 3.")
        assertNextLine("Current Drift: Ship 7 drifted from tile 10 to tile 12.")
        assertNextLine("Event: Event 1 of type OIL_SPILL happened.")
        skipLines(3)
        assertNextLine("Garbage Collection: Ship 1 collected 500 of garbage OIL with 4.")
        assertNextLine("Garbage Collection: Ship 2 collected 500 of garbage OIL with 7.")
        assertNextLine("Garbage Collection: Ship 3 collected 500 of garbage OIL with 9.")
        assertNextLine("Garbage Collection: Ship 4 collected 500 of garbage OIL with 8.")
        assertNextLine("Garbage Collection: Ship 5 collected 500 of garbage OIL with 2.")
        assertNextLine("Garbage Collection: Ship 5 collected 500 of garbage OIL with 5.")
        assertNextLine("Garbage Collection: Ship 7 collected 600 of garbage OIL with 3.")
        assertNextLine("Garbage Collection: Ship 7 collected 400 of garbage OIL with 10.")
    }
}
