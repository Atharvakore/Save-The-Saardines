package de.unisaarland.cs.se.selab.systemtest.collectests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class PlasticCollect : ExampleSystemTestExtension() {
    override val description = "collect plastic when there are only one collect ship"
    override val corporations = "corporationJsons/corpCollectPlastic.json"
    override val scenario = "scenarioJsons/collectPlasticScenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "Plastic Collect "
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpCollectPlastic.json successfully parsed and validated.")
        assertNextLine("Initialization Info: collectPlasticScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 1 collected 1000 of garbage PLASTIC with 1.")
        skipLines(5)
        assertNextLine("Simulation Statistics: Corporation 1 collected 1000 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 1000.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
    }
}
