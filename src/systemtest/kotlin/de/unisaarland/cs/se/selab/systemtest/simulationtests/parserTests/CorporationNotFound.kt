package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * mutant test
 */
class CorporationNotFound : ExampleSystemTestExtension() {
    override val description = "corporations not found"
    override val corporations = "corporationJsons/dummyCorp.json"
    override val scenario = "Try404/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "corporationNotFound"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: dummyCorp.json is invalid.")
    }
}
