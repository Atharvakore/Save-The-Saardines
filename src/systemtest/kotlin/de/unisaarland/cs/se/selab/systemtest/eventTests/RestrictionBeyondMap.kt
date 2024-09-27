package de.unisaarland.cs.se.selab.systemtest.eventTests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTestAssertionError
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import de.unisaarland.cs.se.selab.systemtest.utils.Logs

/**
 * Restrict have radius beyond the map
 */
class RestrictionBeyondMap : ExampleSystemTestExtension() {
    override val description = "Restriction Event radius is beyond the map"
    override val corporations = "corporationJsons/OilPlasticChemical.json"
    override val scenario = "scenarioJsons/restrictionBeyondMap.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "RestrictionBeyondMap"
    override val maxTicks = 5
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: OilPlasticChemical.json successfully parsed and validated.")
        assertNextLine("Initialization Info: restrictionBeyondMap.json successfully parsed and validated.")
        val expectedString = "Simulation Statistics: Corporation 1 collected 0 of garbage."
        if (skipUntilLogType(Logs.SIMULATION_STATISTICS) != expectedString) {
            throw SystemTestAssertionError("Collected plastic should be 0!")
        }
        assertNextLine("Simulation Statistics: Corporation 2 collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Corporation 3 collected 0 of garbage.")
        skipLines(3)
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 200.")
    }
}
