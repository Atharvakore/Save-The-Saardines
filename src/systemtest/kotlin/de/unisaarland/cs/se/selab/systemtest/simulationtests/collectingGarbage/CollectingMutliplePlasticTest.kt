package de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests collecting plastic by multiple ships
 * */
class CollectingMutliplePlasticTest : ExampleSystemTestExtension() {
    override val description = "tests collecting plastic multiple"
    override val corporations = "unloadingJsons/plasticCorporation.Json"
    override val scenario = "unloadingJsons/plasticScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CollectingMutliplePlasticTest "
    override val maxTicks = 1

    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 1 collected 5000 of garbage PLASTIC with 1.")

        skipUntilString("Corporation Action: Corporation 2 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 2 collected 5000 of garbage PLASTIC with 1.")
    }
}
