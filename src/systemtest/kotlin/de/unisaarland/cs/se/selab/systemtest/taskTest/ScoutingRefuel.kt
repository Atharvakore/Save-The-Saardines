package de.unisaarland.cs.se.selab.systemtest.taskTest

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class ScoutingRefuel : ExampleSystemTestExtension() {
    override val description = "tests a scouting ship refuel behaviour"
    override val corporations = "corporationJsons/scoutingRefuel.json"
    override val scenario = "scenarioJsons/exploreTest.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "find scouting ship refuel"
    override val maxTicks = 6
    override suspend fun run() {
        assertNextLine("Initialization Info: map_medium_01.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scoutingRefuel.json successfully parsed and validated.")
        assertNextLine("Initialization Info: exploreTest.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 25 to tile 12.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 2 moved with speed 50 to tile 17.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 2 moved with speed 75 to tile 52.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 2 moved with speed 100 to tile 19.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 2 moved with speed 100 to tile 59.")
        skipLines(8)
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Refueling: Ship 2 refueled at harbor 59.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
    }
}
