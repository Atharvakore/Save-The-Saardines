package de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
/** Scouting Ship performs a task and return to harbor*/
class HardChoice : ExampleSystemTestExtension() {
    override val description = "Test Ship performing exploring task and collecting garbage with reward"
    override val corporations = "performTask/corporation.json"
    override val scenario = "performTask/scenario.json"
    override val map = "performTask/map.json"
    override val name = "HardChoice"
    override val maxTicks = 10
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 5.")
        skipLines(1)
        assertNextLine("Garbage Collection: Ship 2 collected 1000 of garbage OIL with 2.")
        skipLines(3)
        assertNextLine("Task: Task 1 of type EXPLORE with ship 1 is added with destination 15.")
        assertNextLine("Task: Task 2 of type COLLECT with ship 2 is added with destination 8.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 11.")
        skipLines(4)
        assertNextLine("Reward: Task 2: Ship 1 received reward of type CONTAINER.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 1 moved with speed 30 to tile 15.")
        skipLines(4)
        assertNextLine("Reward: Task 1: Ship 2 received reward of type TELESCOPE.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 1 moved with speed 40 to tile 9.")
        skipLines(1)
        skipUntilString("Simulation Info: Tick 4 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 9.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 7.")
        skipLines(1)
        assertNextLine("Garbage Collection: Ship 1 collected 1000 of garbage OIL with 1.")
        skipUntilString("Simulation Info: Tick 5 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 13.")
    }
}
