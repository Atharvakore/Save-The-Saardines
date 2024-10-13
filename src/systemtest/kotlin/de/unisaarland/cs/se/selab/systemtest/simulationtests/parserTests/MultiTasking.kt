package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
/** Test for Not Specified properties*/
class MultiTasking : ExampleSystemTestExtension() {
    override val map = "MultiTasking/smallMap1.json"
    override val corporations = "MultiTasking/corporations.json"
    override val scenario = "MultiTasking/scenario.json"
    override val description = "Checks when a land tile is near a deep ocean tile"
    override val maxTicks = 0
    override val name = "LandNextDeep"
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenario.json is invalid.")
        assertEnd()
    }
}
