package de.unisaarland.cs.se.selab.systemtest.eventTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class PirateEventTest : ExampleSystemTestExtension() {
    override val description = "tests the pirate event "
    override val corporations = "corporationJsons/pirateAttackTest.json"
    override val scenario = "scenarioJsons/pirateAttackTestScenario.json"
    override val map = "mapFiles/elevenTileMap.json"
    override val name = "tests if a ship (which collected garbage) gets stolen by the attack"
    override val maxTicks = 2

    override suspend fun run() {
        assertNextLine("Initialization Info: elevenTileMap.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: pirateAttackTest.json" +
                " successfully parsed and validated."
        )

        assertNextLine("Initialization Info: pirateAttackTestScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        skipLines(2)
        assertNextLine("Garbage Collection: Ship 2 collected 900 of garbage PLASTIC with 1.")
        skipLines(3)
        assertNextLine("Event: Event 1 of type PIRATE_ATTACK happened.")
        assertNextLine("Simulation Info: Tick 1 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 900 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 900.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
    }
}
