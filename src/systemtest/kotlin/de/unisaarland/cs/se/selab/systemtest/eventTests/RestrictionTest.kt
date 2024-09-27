package de.unisaarland.cs.se.selab.systemtest.eventTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class RestrictionTest : ExampleSystemTestExtension() {
    override val description = "tests the restriction event "
    override val corporations = "corporationJsons/restrictionTest.json"
    override val scenario = "scenarioJsons/restrictionTestScenario.json"
    override val map = "mapFiles/elevenTileMap.json"
    override val name = "tests if a ships waits until the restriction is over"
    override val maxTicks = 3

    override suspend fun run() {
        assertNextLine("Initialization Info: elevenTileMap.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: restrictionTest.json" +
                " successfully parsed and validated."
        )

        assertNextLine("Initialization Info: restrictionTestScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 8.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 7.")
        skipLines(4)
        assertNextLine("Event: Event 1 of type RESTRICTION happened.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 2 moved with speed 20 to tile 3.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 7.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 20 to tile 1.")
    }
}
