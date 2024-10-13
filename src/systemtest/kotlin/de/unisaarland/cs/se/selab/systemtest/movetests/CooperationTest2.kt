package de.unisaarland.cs.se.selab.systemtest.movetests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class CooperationTest2 : ExampleSystemTestExtension() {
    override val description = "tests cooperating with another ship "
    override val corporations = "corporationJsons/cooperationTest2Corporations.json"
    override val scenario = "scenarioJsons/cooperationTest2Scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "tests if corporations cooperate with another "
    override val maxTicks = 5

    override suspend fun run() {
        assertNextLine("Initialization Info: map_medium_01.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: cooperationTest2Corporations.json" +
                " successfully parsed and validated."
        )
        assertNextLine("Initialization Info: cooperationTest2Scenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 6.")
        skipLines(5)
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 8.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 1 moved with speed 50 to tile 7.")
        skipLines(5)
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 7.")
        skipLines(1)
        assertNextLine("Corporation Action: Corporation 2 is starting to cooperate with other corporations.")
        assertNextLine("Cooperation: Corporation 2 cooperated with corporation 1 with ship 2 to ship 1.")
    }
}
