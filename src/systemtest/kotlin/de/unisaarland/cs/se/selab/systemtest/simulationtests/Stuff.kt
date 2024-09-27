package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Drifting, Exploring, Scouting, Collecting, Returning*/
class Stuff : ExampleSystemTestExtension() {
    override val description = "stuff"
    override val corporations = "stuff/c.json"
    override val scenario = "stuff/s.json"
    override val map = "stuff/m.json"
    override val name = "stuff"
    override val maxTicks = 2
    override suspend fun run() {
        skipLines(3);
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 0 is starting to move its ships.")
        assertNextLine("Corporation Action: Corporation 0 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 0 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 0 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 0 finished its actions.")
        assertNextLine("Task: Task 0 of type FIND with ship 0 is added with destination 1.")
        assertNextLine("Simulation Info: Tick 1 started.")
        assertNextLine("Corporation Action: Corporation 0 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 0 moved with speed 10 to tile 1.")
        assertNextLine("Corporation Action: Corporation 0 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 0 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 0 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 0 finished its actions.")
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 0 collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
        assertEnd()
    }
}
