package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * Tests Tasks
 */
class RewardTest : ExampleSystemTestExtension() {
    override val description = "tests Task Assignment and Working"
    override val corporations = "rewardTestJsons/corporation.json"
    override val scenario = "rewardTestJsons/scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "RewardTest"
    override val maxTicks = 1

    override suspend fun run() {
        assertLine("Initialization Info: map_medium_01.json successfully parsed and validated.", true)
        assertNextLine("Initialization Info: corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 13.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Current Drift: Ship 2 drifted from tile 36 to tile 35.")
        assertNextLine("Task: Task 1 of type FIND with ship 1 is added with destination 13.")
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
    }
}
