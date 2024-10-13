package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class AtleastOneCorpInSim : ExampleSystemTestExtension() {
    override val description = "Corp without Ship"
    override val corporations = "corporationJsons/atleastOneCorpInSim.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "AtleastOneCorpInSim"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: atleastOneCorpInSim.json is invalid.")
    }
}
