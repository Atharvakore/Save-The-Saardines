package de.unisaarland.cs.se.selab.systemtest.scenarioTests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTestAssertionError
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import de.unisaarland.cs.se.selab.systemtest.utils.Logs

/**
 * Basic 6 Tile Test. deep ocean on right with a current pushing left
 */
class LooooonggggTickkkkkkk : ExampleSystemTestExtension() {
    override val description = "Do 50 tick simulation and Pray.."
    override val corporations = "corporationJsons/toManyShipsAnsCorpo.json"
    override val scenario = "scenarioJsons/dontBreakPlease.json"
    override val map = "mapFiles/bigMap1.json"
    override val name = "LONGLONGLONGLONG"
    override val maxTicks = 50

    override suspend fun run() {
        val expectedString = "Simulation Statistics: Corporation 1 collected 19000 of garbage."
        if (skipUntilLogType(Logs.SIMULATION_STATISTICS) != expectedString) {
            throw SystemTestAssertionError("Collected plastic should be 0!")
        }
        assertNextLine("Simulation Statistics: Corporation 2 collected 23000 of garbage.")
        assertNextLine("Simulation Statistics: Corporation 3 collected 23000 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 65000.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
    }
}
