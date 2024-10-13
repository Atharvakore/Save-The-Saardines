package de.unisaarland.cs.se.selab.systemtest.simulationtests.scouting

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Perform Scouting behaviour*/
class AdventureTime : ExampleSystemTestExtension() {
    override val description = "Test Exploring"
    override val corporations = "performTask/corporationAdventure.json"
    override val scenario = "performTask/scenarioAdventure.json"
    override val map = "performTask/map.json"
    override val name = "AdventureTime"
    override val maxTicks = 13
    override suspend fun run() {
        skipUntilString("Task: Task 1 of type EXPLORE with ship 1 is added with destination 15.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 2.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 4.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 2 collected 3000 of garbage PLASTIC with 1.")
        skipUntilString("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 11.")
        skipUntilString("Simulation Info: Tick 3 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 30 to tile 15.")
        skipLines(4)
        assertNextLine("Reward: Task 1: Ship 1 received reward of type TELESCOPE.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 11.")
        skipUntilString("Simulation Info: Tick 5 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 2.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 3.")
        skipUntilString("Simulation Info: Tick 6 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 30 to tile 1.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 2.")
        skipUntilString("Simulation Info: Tick 7 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 1.")
        skipLines(1)
        assertNextLine("Garbage Collection: Ship 2 collected 2000 of garbage PLASTIC with 2.")
        skipUntilString("Simulation Info: Tick 8 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 2.")
        skipUntilString("Simulation Info: Tick 9 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 4.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 5.")
        skipUntilString("Simulation Info: Tick 10 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 30 to tile 1.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 9.")
        skipUntilString("Simulation Info: Tick 11 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 40 to tile 8.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 13.")
        skipUntilString("Simulation Info: Tick 12 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 50 to tile 1.")
        skipUntilString("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Unload: Ship 2 unloaded 5000 of garbage PLASTIC at harbor 13.")
    }
}
