package de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * Test the behaviour of collecting ship with full capacity
 * */
class ReturnToHomeWater : ExampleSystemTestExtension() {
    override val corporations = "garbageCollecting/corporationPlastic.json"
    override val description = "Test the behaviour of collecting ship with full capacity"
    override val map = "garbageCollecting/map.json"
    override val maxTicks = 1
    override val name = "Return To Home Water"
    override val scenario = "garbageCollecting/scenarioMoreGarbage.json"
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporationPlastic.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenarioMoreGarbage.json successfully parsed and validated.")

        // simulation Start
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 7.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 2 collected 1000 of garbage PLASTIC with 2.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        // tick 1
//        assertNextLine(" Simulation Info: Tick 1 started.")
//        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
//        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 4.")
//        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
//        assertNextLine("Garbage Collection: Ship 2 collected 1000 of garbage PLASTIC with 1.")
//        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
//        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
//        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
//        // tick 2
//        assertNextLine("Simulation Info: Tick 2 started.")
//        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
//        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 1.")
//        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
//        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
//        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
//        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        // simulation end
    }
}
