package de.unisaarland.cs.se.selab.systemtest.simulationtests.scouting

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Test Exploring and moving to a Oil Event */
class GodVision : ExampleSystemTestExtension() {
    override val description = "Test Exploring and moving to a Oil Event"
    override val corporations = "godVision/corporation.json"
    override val scenario = "godVision/scenario.json"
    override val map = "godVision/map.json"
    override val name = "God Vision"
    override val maxTicks = 15
    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 25 to tile 14.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 2 moved with speed 50 to tile 19.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 2 moved with speed 75 to tile 12.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 2 moved with speed 100 to tile 69.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 2 moved with speed 100 to tile 12.")
        skipUntilString("Event: Event 1 of type OIL_SPILL happened.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 2 moved with speed 100 to tile 52.")
        skipUntilString("Refueling: Ship 2 refueled at harbor 52.")
        skipLines(1)
        assertNextLine("Simulation Info: Tick 7 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 2 moved with speed 25 to tile 54.")
    }
}
