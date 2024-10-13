package de.unisaarland.cs.se.selab.systemtest.collectests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class PlasticCannotCollect : ExampleSystemTestExtension() {
    override val description = "Cannot collect plastic when there are not enough capacity"
    override val corporations = "corporationJsons/corpCannotCollect.json"
    override val scenario = "scenarioJsons/cannotCollectPlastic.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "Plastic Collect "
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpCannotCollect.json successfully parsed and validated.")
        assertNextLine("Initialization Info: cannotCollectPlastic.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        skipLines(2)
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        skipLines(5)
        assertNextLine("Simulation Statistics: Corporation 1 collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 3000.")
    }
}
