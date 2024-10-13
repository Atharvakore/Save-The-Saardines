package de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Collect and return to Harbor*/
class MoveNearHome : ExampleSystemTestExtension() {
    override val corporations = "garbageCollecting/corporation.json"
    override val description = "Test the behaviour of collecting ship moving through harbor"
    override val map = "garbageCollecting/map.json"
    override val maxTicks = 3
    override val name = "Move Through Harbor"
    override val scenario = "garbageCollecting/scenario.json"
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated.")

        // simulation Start
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 7.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 2 collected 1000 of garbage OIL with 2.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        // tick 1
        skipUntilString("Simulation Info: Tick 1 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 4.")
        skipLines(1)
        assertNextLine("Garbage Collection: Ship 2 collected 1000 of garbage OIL with 1.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        // tick 2
        assertNextLine("Simulation Info: Tick 2 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 1.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")

        // simulation end
        skipUntilString("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 2000 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 2000.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
        assertEnd()
    }
}
