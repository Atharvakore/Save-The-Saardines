package de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test collecting chemicals garbage
 * */
class CollectingChemicalsTest : ExampleSystemTestExtension() {
    override val description = "tests collecting chemicals"
    override val corporations = "unloadingJsons/unloadingCorporation.json"
    override val scenario = "unloadingJsons/unloadingScenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CollectingChemicalsTest"
    override val maxTicks = 1

    override suspend fun run() {
        skipUntilString("Garbage Collection: Ship 4 collected 1000 of garbage OIL with 1.")
        assertNextLine("Garbage Collection: Ship 5 collected 1000 of garbage CHEMICALS with 2.")
    }
}
