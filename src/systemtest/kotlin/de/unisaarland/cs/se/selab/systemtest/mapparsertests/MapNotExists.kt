package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class MapNotExists : ExampleSystemTestExtension() {
    override val description = "json file is empty"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/emptyFile.json"
    override val name = "MapNotExists"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: emptyFile.json is invalid.")
    }
}
