package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class HarborWithoutCorp : ExampleSystemTestExtension() {
    override val description = "Harbor without corporation"
    override val corporations = "invalidAssets/harborCorpInvalid.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "HarborWithoutCorp"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: harborCorpInvalid.json is invalid.")
    }
}
