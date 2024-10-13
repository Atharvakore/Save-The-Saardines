package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class ShoreHasCurrent : ExampleSystemTestExtension() {
    override val description = "shore tile can not have current"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/smallMapShoreCurrent.json"
    override val name = "ShoreHasCurrent"
    override val maxTicks = 0

    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapShoreCurrent.json is invalid.")
    }
}
