package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class ExampleSystemTestTick1 : ExampleSystemTestExtension() {
    override val description = "Tests everything after 1 tick"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "Example Test 1 Tick"
    override val maxTicks = 1
    override suspend fun run() {
        val corp1MoveShips = "Corporation Action: Corporation 1 is starting to move its ships."
        val corp1CollectGarbage = "Corporation Action: Corporation 1 is starting to collect garbage."
        val corp1Coop = "Corporation Action: Corporation 1 is starting to cooperate with other corporations."
        val corp1Refuel = "Corporation Action: Corporation 1 is starting to refuel."
        val corp1Finished = "Corporation Action: Corporation 1 finished its actions."

        val corp2MoveShips = "Corporation Action: Corporation 2 is starting to move its ships."
        val corp2CollectGarbage = "Corporation Action: Corporation 2 is starting to collect garbage."
        val corp2Coop = "Corporation Action: Corporation 2 is starting to cooperate with other corporations."
        val corp2Refuel = "Corporation Action: Corporation 2 is starting to refuel."
        val corp2Finished = "Corporation Action: Corporation 2 finished its actions."

        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine(corp1MoveShips)
        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 14.")
        assertNextLine(corp1CollectGarbage)
        assertNextLine(corp1Coop)
        assertNextLine(corp1Refuel)
        assertNextLine(corp1Finished)
        assertNextLine(corp2MoveShips)
        assertNextLine(corp2CollectGarbage)
        assertNextLine(corp2Coop)
        assertNextLine(corp2Refuel)
        assertNextLine(corp2Finished)
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Corporation 2 collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 1000.")
    }
}
