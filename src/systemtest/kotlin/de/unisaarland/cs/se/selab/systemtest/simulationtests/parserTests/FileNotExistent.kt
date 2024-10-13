package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * mutant test
 */
class FileNotExistent : ExampleSystemTestExtension() {
    override val description = "no ships for corp"
    override val corporations = "corporationJsons/corpNoShips.json"
    override val scenario = "Try404/scenario.json"
    override val map = "mapFiles/dummy.json"
    override val name = "corporation with 0 ships"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: dummy.json is invalid.")
    }
}
