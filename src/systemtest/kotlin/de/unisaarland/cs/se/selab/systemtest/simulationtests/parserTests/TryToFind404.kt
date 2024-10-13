package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
/** Test for Not Specified properties*/
class TryToFind404 : ExampleSystemTestExtension() {
    override val map = "Try404/smallMap1.json"
    override val corporations = "Try404/corporations.json"
    override val scenario = "Try404/scenario.json"
    override val description = "gives ship invalid location"
    override val maxTicks = 0
    override val name = "404"
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporations.json is invalid.")
        assertEnd()
    }
}
