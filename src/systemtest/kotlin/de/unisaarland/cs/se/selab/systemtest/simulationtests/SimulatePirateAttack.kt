package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.api.SystemTestAssertionError
import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import de.unisaarland.cs.se.selab.systemtest.utils.Logs

/** Simulate a pirate attack over a ship*/
class SimulatePirateAttack : ExampleSystemTestExtension() {
    override val description = "Test how a pirateAttack will happen"
    override val corporations = "corporationJsons/smallCorporation.json"
    override val scenario = "scenarioJsons/smallEvent.json"
    override val map = "mapFiles/smallMap.json"
    override val name = "SimulatePirateAttack"
    override val maxTicks = 1
    override suspend fun run() {
        val expected = "Event: Event 1 of type PIRATE_ATTACK happened."
        if (skipUntilLogType(Logs.EVENT) != expected) {
            throw SystemTestAssertionError("Event should have been started")
        }
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
    }
}
