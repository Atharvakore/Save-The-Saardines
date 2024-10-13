package de.unisaarland.cs.se.selab.systemtest.collectests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class NotCollect : ExampleSystemTestExtension() {
    override val description = "Tests a corporation with only one collect ship won't collect"
    override val corporations = "corporationJsons/corpNotCollect.json"
    override val scenario = "scenarioJsons/noCollectScenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "NotCollect"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpNotCollect.json successfully parsed and validated.")
        assertNextLine("Initialization Info: noCollectScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
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
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 3000.")
    }
}
