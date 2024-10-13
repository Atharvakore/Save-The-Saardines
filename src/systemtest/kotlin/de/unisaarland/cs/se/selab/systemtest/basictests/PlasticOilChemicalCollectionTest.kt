package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTestAssertionError
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import de.unisaarland.cs.se.selab.systemtest.utils.Logs

/**
 * collect all type of garbage
 */
class PlasticOilChemicalCollectionTest : ExampleSystemTestExtension() {
    override val description = "Collecting all type of Garbage including Oil Spill"
    override val corporations = "corporationJsons/OilPlasticChemical.json"
    override val scenario = "scenarioJsons/smallFullSimulation.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "AllGarbageTypeCollect"
    override val maxTicks = 3
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: OilPlasticChemical.json successfully parsed and validated.")
        assertNextLine("Initialization Info: smallFullSimulation.json successfully parsed and validated.")
        val expectedString = "Simulation Statistics: Corporation 1 collected 1250 of garbage."
        if (skipUntilLogType(Logs.SIMULATION_STATISTICS) != expectedString) {
            throw SystemTestAssertionError("Collected plastic should be 0!")
        }
        assertNextLine("Simulation Statistics: Corporation 2 collected 500 of garbage.")
        assertNextLine("Simulation Statistics: Corporation 3 collected 250 of garbage.")
        skipLines(3)
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
    }
}
