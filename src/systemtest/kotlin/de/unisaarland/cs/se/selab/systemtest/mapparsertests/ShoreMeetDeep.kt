package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class ShoreMeetDeep : ExampleSystemTestExtension() {
    override val description = "shore tile can not have deep ocean as neighbor"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/shoreMeetDeepMap.json"
    override val name = "ShoreMeetDeep"
    override val maxTicks = 0

    override suspend fun run() {
        assertNextLine("Initialization Info: shoreMeetDeepMap.json is invalid.")
    }
}
