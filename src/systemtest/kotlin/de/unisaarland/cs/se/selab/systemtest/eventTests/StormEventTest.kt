package de.unisaarland.cs.se.selab.systemtest.eventTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class StormEventTest : ExampleSystemTestExtension() {
    override val description = "tests multiple storm events happened at the same tick "
    override val corporations = "corporationJsons/stormCorp.json"
    override val scenario = "scenarioJsons/StormScenario.json"
    override val map = "mapFiles/TestMap.json"
    override val name = "Multi Storm Test"
    override val maxTicks = 1

    override suspend fun run() {
        assertNextLine("Initialization Info: TestMap.json successfully parsed and validated.")
        assertNextLine("Initialization Info: stormCorp.json successfully parsed and validated.")
        assertNextLine("Initialization Info: StormScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 6.")
        skipLines(4)
        assertNextLine("Event: Event 1 of type STORM happened.")
        assertNextLine("Event: Event 2 of type STORM happened.")
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 2210.")
    }
}
