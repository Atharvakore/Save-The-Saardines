package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class DeepHasHarbor : ExampleSystemTestExtension() {
    override val description = "deep ocean can not have harbor"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/smallMapDeepHarbor.json"
    override val name = "DeepHasHarbor"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapDeepHarbor.json is invalid.")
    }
}
