package de.unisaarland.cs.se.selab.systemtest.collectests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class MultiTypeCollect : ExampleSystemTestExtension() {
    override val description = "Tests a corporation with only one collect type"
    override val corporations = "corporationJsons/multiCollect.json"
    override val scenario = "scenarioJsons/multiCollectScenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "multiCollect"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: multiCollect.json successfully parsed and validated.")
        assertNextLine("Initialization Info: multiCollectScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 3.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 2 collected 2000 of garbage PLASTIC with 1.")
        skipLines(3)
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 2000 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 2000.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 1800.")
    }
}
