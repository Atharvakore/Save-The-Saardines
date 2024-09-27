package de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests the mutant
 * */
class Error404Test : ExampleSystemTestExtension() {
    override val description = "tests for corporation visibility range for sc ship"
    override val corporations = "mutants/Error404Corporation.json"
    override val scenario = "mutants/Error404Scenario.json"
    override val map = "mapFiles/smallMap2.json"
    override val name = "Error404Test"
    override val maxTicks = 1

    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap2.json successfully parsed and validated.")
        assertNextLine("Initialization Info: Error404Corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: Error404Scenario.json is invalid.")
    }
}
