package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests general garabge collecting
 * */
class CollectAndRefuelTest : ExampleSystemTestExtension() {
    override val description = "test collect then refuel"
    override val corporations = "collectAndRefuelTestJsons/corporation.json"
    override val scenario = "collectAndRefuelTestJsons/scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CollectAndRefuelTest"
    override val maxTicks = 4

    override suspend fun run() {
        assertNextLine("Initialization Info: map_medium_01.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 3 moved with speed 25 to tile 63.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Simulation Info: Tick 1 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 53.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 900.")
    }
}
