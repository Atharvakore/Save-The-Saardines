package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorpWithGarbageTypeWithoutCollectingShip : ExampleSystemTestExtension() {
    override val description = "Corp With Garbage Type Without Collecting Ship"
    override val corporations = "corporationJsons/corpWithGarbageTypeWithoutCollectingShip.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CorpWithGarbageTypeWithoutCollectingShip"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpWithGarbageTypeWithoutCollectingShip.json is invalid.")
    }
}
