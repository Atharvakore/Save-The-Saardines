package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class ShallowHasHarbor : ExampleSystemTestExtension() {
    override val description = "shallow ocean can not have harbor"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/smallMapShallowHarbor.json"
    override val name = "ShallowHasHarbor"
    override val maxTicks = 0

    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapShallowHarbor.json is invalid.")
    }
}
