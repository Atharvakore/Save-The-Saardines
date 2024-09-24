package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * mutant test
 */
class ScenarioNotFound : ExampleSystemTestExtension() {
    override val description = "no ships for corp"
    override val corporations = "corporationJsons/corporations.json"
    override val scenario = "scenarioJsons/scenarioIsNotHere.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "corporation with 0 ships"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenarioIsNotHere.json is invalid")
    }
}
