package de.unisaarland.cs.se.selab.systemtest.simulationtests.scouting

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
/** Perform Scouting behaviour*/
class AdventureTime : ExampleSystemTestExtension() {
    override val description = "Test Exploring"
    override val corporations = "performTask/corporationAdventure.json"
    override val scenario = "performTask/scenarioAdventure.json"
    override val map = "performTask/map.json"
    override val name = "AdventureTime"
    override val maxTicks = 10
    override suspend fun run() {
        skipUntilString("Simulation Info: Tick 0 started.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 2.")
        skipLines(4)
        assertNextLine("Task: Task 1 of type EXPLORE with ship 1 is added with destination 15.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 11.")

    }
}