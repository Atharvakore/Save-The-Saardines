package de.unisaarland.cs.se.selab.systemtest.taskTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class Coropro : ExampleSystemTestExtension() {
    override val description = "tests if a ship which gets a telescope moves to garbage now"
    override val corporations = "corporationJsons/corpoCollab.json"
    override val scenario = "scenarioJsons/collabScenario.json"
    override val map = "mapFiles/map.json"
    override val name = "Telescope Test"
    override val maxTicks = 7
    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
    }
}
