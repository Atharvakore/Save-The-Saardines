package de.unisaarland.cs.se.selab.systemtest.mapparsertests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class ShallowHasCurrent : ExampleSystemTestExtension() {
    override val description = "shallow ocean can not have current"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "invalidMapFiles/smallMapShallowCurrent.json"
    override val name = "ShallowHasCurrent"
    override val maxTicks = 0

    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapShallowCurrent.json is invalid.")
    }
}
