package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
/** Test for Not Specified properties*/
class EverythingInOne : ExampleSystemTestExtension() {
    override val corporations = "corporationJsons/corporationCorrect.json"
    override val description = "Test for correct properties of rewards(the other ones were tested locally)"
    override val map = "mapFiles/map.json"
    override val maxTicks = 0
    override val name = "Everything in one"
    override val scenario = "scenarioJsons/scenarioEverythingInOne.json"
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporationCorrect.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenarioEverythingInOne.json is invalid.")
        assertEnd()
    }
}
