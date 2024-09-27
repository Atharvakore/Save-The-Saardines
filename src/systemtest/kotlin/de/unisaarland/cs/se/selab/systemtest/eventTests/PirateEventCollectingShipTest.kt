package de.unisaarland.cs.se.selab.systemtest.eventTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class PirateEventCollectingShipTest : ExampleSystemTestExtension() {
    override val description = "tests the pirate event after collected the garbage"
    override val corporations = "corporationJsons/pirateEventCorp.json"
    override val scenario = "scenarioJsons/PirateEventScenario.json"
    override val map = "mapFiles/TestMap.json"
    override val name = "tests if a collecting ship will get attacked after collecting garbage"
    override val maxTicks = 2

    override suspend fun run() {
        assertNextLine("Initialization Info: TestMap.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: pirateEventCorp.json successfully parsed and validated."
        )

        assertNextLine("Initialization Info: PirateEventScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 34.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 24.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 2 collected 100 of garbage OIL with 1.")
        skipLines(4)
        assertNextLine("Event: Event 2 of type PIRATE_ATTACK happened.")
        assertNextLine("Simulation Info: Tick 1 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
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
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 300.")
    }
}
