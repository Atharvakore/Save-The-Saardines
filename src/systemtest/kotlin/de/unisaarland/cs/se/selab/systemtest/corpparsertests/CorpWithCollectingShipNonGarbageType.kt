package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorpWithCollectingShipNonGarbageType : ExampleSystemTestExtension() {
    override val description = "Corp With Collecting Ship Non Garbage Type of Corp"
    override val corporations = "corporationJsons/corpWithCollectingShipNonGarbageType.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CorpWithCollectingShipNonGarbageType"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpWithCollectingShipNonGarbageType.json is invalid.")
    }
}
