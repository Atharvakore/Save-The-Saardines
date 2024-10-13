package de.unisaarland.cs.se.selab.systemtest.corpparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorpWithInvalidGarbage : ExampleSystemTestExtension() {
    override val description = "Corp has wrong garbage type"
    override val corporations = "invalidAssets/corpGarbageTypeInvalid.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CorpWrongGarbageType"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpGarbageTypeInvalid.json is invalid.")
    }
}
