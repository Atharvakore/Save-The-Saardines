package de.unisaarland.cs.se.selab.systemtest.taskTest

import de.unisaarland.cs.se.selab.systemtest.api.SystemTestAssertionError
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import de.unisaarland.cs.se.selab.systemtest.utils.Logs

/**
 * collect all type of garbage
 */
class CollectTaskTest : ExampleSystemTestExtension() {
    override val description = "Collect task test when ship obtain container for plastic"
    override val corporations = "corporationJsons/OilPlasticChemical.json"
    override val scenario = "scenarioJsons/collectTaskTest.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CollectTaskTest"
    override val maxTicks = 5
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: OilPlasticChemical.json successfully parsed and validated.")
        assertNextLine("Initialization Info: collectTaskTest.json successfully parsed and validated.")
        val expectedString = "Simulation Statistics: Corporation 1 collected 2000 of garbage."
        if (skipUntilLogType(Logs.SIMULATION_STATISTICS) != expectedString) {
            throw SystemTestAssertionError("Collected plastic should be 0!")
        }
        assertNextLine("Simulation Statistics: Corporation 2 collected 6000 of garbage.")
        assertNextLine("Simulation Statistics: Corporation 3 collected 1000 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 7000.")
        skipLines(2)
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
    }
}
