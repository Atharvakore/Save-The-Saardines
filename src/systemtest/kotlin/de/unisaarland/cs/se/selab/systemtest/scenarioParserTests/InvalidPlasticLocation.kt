package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class InvalidPlasticLocation : ExampleSystemTestExtension() {
    override val description = "invalid event ID which is negative"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/invalidScenario/plasticLocationLand.json"
    override val map = "mapFiles/smallMapLand.json"
    override val name = "invalid plastic location at the Land Tile"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapLand.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: plasticLocationLand.json is invalid.")
    }
}
