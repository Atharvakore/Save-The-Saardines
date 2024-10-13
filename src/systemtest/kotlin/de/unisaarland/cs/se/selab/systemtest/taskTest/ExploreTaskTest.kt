package de.unisaarland.cs.se.selab.systemtest.taskTest

import de.unisaarland.cs.se.selab.systemtest.api.SystemTestAssertionError
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import de.unisaarland.cs.se.selab.systemtest.utils.Logs

/**
 * collect all type of garbage
 */
class ExploreTaskTest : ExampleSystemTestExtension() {
    override val description = "Explore task test for collecting ship with telescope"
    override val corporations = "corporationJsons/exploreCompany.json"
    override val scenario = "scenarioJsons/exploreTaskTest.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "Explore Task Test"
    override val maxTicks = 5
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: exploreCompany.json successfully parsed and validated.")
        assertNextLine("Initialization Info: exploreTaskTest.json successfully parsed and validated.")
        val expectedString = "Simulation Statistics: Corporation 1 collected 1500 of garbage."
        if (skipUntilLogType(Logs.SIMULATION_STATISTICS) != expectedString) {
            throw SystemTestAssertionError("Collected plastic should be 0!")
        }
        skipLines(1)
        assertNextLine("Simulation Statistics: Total amount of oil collected: 1500.")
        skipLines(1)
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 2000.")
    }
}
