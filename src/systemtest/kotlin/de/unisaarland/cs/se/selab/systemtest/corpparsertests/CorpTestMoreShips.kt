package de.unisaarland.cs.se.selab.systemtest.corpparsertests
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CorpTestMoreShips : ExampleSystemTestExtension() {
    override val description = "Corp with more ships in the Corporation file, could be a parsing "
    override val corporations = "collectTest/collectTestCorp.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CorpTestMoreShips"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: collectTestCorp.json successfully parsed and validated.")
    }
}
