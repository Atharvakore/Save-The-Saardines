package de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
/** Collect and return to Harbor*/
class ReturnToHomeWater : ExampleSystemTestExtension() {
    override val corporations = "garbageCollecting/corporation.json"
    override val description = "Test the behaviour of collecting ship"
    override val map = "garbageCollecting/map.json"
    override val maxTicks = 5
    override val name = "Return To Home Water"
    override val scenario = "garbageCollecting/scenario.json"
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated.")

        // simulation Start
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 7.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 2 collected 1000 of garbage OIL with 2.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine(" Simulation Info: Tick 1 started.")
    }
}
