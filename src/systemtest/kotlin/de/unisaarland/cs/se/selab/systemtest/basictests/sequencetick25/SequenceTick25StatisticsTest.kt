package de.unisaarland.cs.se.selab.systemtest.basictests.sequencetick25

import de.unisaarland.cs.se.selab.systemtest.api.SystemTestAssertionError
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import de.unisaarland.cs.se.selab.systemtest.utils.Logs

/**
 * test the statistics part of tick 25
 * */
class SequenceTick25StatisticsTest : ExampleSystemTestExtension() {
    override val description = "tests statistics part of tick25 sequence from spec"
    override val corporations = "corporationJsons/tick25Corporation.json"
    override val scenario = "scenarioJsons/tick25Scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "SequenceTick25StatisticsTest"
    override val maxTicks = 1
    override suspend fun run() {
        val expectedString = "Simulation Statistics: Corporation 1 collected 0 of garbage."
        if (skipUntilLogType(Logs.SIMULATION_STATISTICS) != expectedString) {
            throw SystemTestAssertionError("Corporation 1 collected 0 of garbage")
        }
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 800.")
    }
}
