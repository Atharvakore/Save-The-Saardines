package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Check if it will accept incorrect pirate attack*/
class IncorrectPropertiesOfPirateAttack : ExampleSystemTestExtension() {
    override val corporations = "corporationJsons/corporationCorrect.json"
    override val description = "Test for incorrect pirate attack props"
    override val map = "mapFiles/map.json"
    override val maxTicks = 0
    override val name = "Incorrect Properties Of Pirate Attack"
    override val scenario = "scenarioJsons/incorrectPirateAttack.json"
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corporationCorrect.json successfully parsed and validated.")
        assertNextLine("Initialization Info: incorrectPirateAttack.json is invalid.")
        assertEnd()
    }
}
