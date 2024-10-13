package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorpWithoutHarbor : ExampleSystemTestExtension() {
    override val description = "Corp without Harbor"
    override val corporations = "corporationJsons/corpWithoutHarbor.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CorpWithoutHarbor"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpWithoutHarbor.json is invalid.")
    }
}
