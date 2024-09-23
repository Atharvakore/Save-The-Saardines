package de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests general garabge collecting
 * */
class CollectingGarbageTest : ExampleSystemTestExtension() {
    override val description = "tests collecting garbage"
    override val corporations = "unloadingJsons/unloadingCorporation.json"
    override val scenario = "unloadingJsons/unloadingScenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CollectingGarbageTest"
    override val maxTicks = 1

    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to collect garbage.")

        assertNextLine("Garbage Collection: Ship 4 collected 1000 of garbage OIL with 1.")
        assertNextLine("Garbage Collection: Ship 5 collected 1000 of garbage CHEMICALS with 2.")
        assertNextLine("Garbage Collection: Ship 6 collected 1000 of garbage PLASTIC with 3.")
    }
}
