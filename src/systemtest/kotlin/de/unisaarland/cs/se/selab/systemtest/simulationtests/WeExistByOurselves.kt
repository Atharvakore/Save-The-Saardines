package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * Test cooperating ships.
 */
class WeExistByOurselves : ExampleSystemTestExtension() {
    override val description = "Test cooperating ships"
    override val corporations = "WeExistByOurselves/corporations.json"
    override val scenario = "WeExistByOurselves/scenario.json"
    override val map = "WeExistByOurselves/map.json"
    override val name = "WeExistByOurselves"
    override val maxTicks = 2

    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 3 moved with speed 10 to tile 3.")
        skipLines(2)
        assertNextLine("Cooperation: Corporation 1 cooperated with corporation 2 with ship 3 to ship 2.")
        skipLines(5)
        assertNextLine("Cooperation: Corporation 2 cooperated with corporation 1 with ship 2 to ship 3.")
        skipLines(4)
        assertNextLine("Ship Movement: Ship 3 moved with speed 10 to tile 2.")
        skipLines(5)
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 2.")
    }
}
