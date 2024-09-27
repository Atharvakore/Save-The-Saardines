package de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test for mutants
 * */
class MultiTaskingTest : ExampleSystemTestExtension() {
    override val description = "tests the mutant"
    override val corporations = "mutants/MultiTaskingCorporation.json"
    override val scenario = "mutants/MultiTaskingScenario.json"
    override val map = "mapFiles/smallMap2.json"
    override val name = "Error404Test"
    override val maxTicks = 1

    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap2.json successfully parsed and validated.")
        assertNextLine("Initialization Info: MultiTaskingCorporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: MultiTaskingScenario.json is invalid.")
    }
}
