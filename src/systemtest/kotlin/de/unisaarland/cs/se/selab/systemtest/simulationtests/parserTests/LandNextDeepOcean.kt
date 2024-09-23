package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
/** Test for Not Specified properties*/
class LandNextDeepOcean : ExampleSystemTestExtension() {
    override val map = "mapFiles/LandNextDeepMap.json"
    override val corporations = "corporationJsons/corporationCorrect.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val description = "Checks when a land tile is near a deep ocean tile"
    override val maxTicks = 0
    override val name = "LandNextDeep"
    override suspend fun run() {
        assertNextLine("Initialization Info: LandNextDeepMap.json is invalid.")
        assertEnd()
    }
}
