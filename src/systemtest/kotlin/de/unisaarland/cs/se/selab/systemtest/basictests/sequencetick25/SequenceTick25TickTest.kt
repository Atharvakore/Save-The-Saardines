package de.unisaarland.cs.se.selab.systemtest.basictests.sequencetick25

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test the rest of the logic of tick25
 * */
class SequenceTick25TickTest : ExampleSystemTestExtension() {
    override val description = "tests tick25 sequence from spec"
    override val corporations = "corporationJsons/tick25Corporation.json"
    override val scenario = "scenarioJsons/tick25Scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "SequenceTick25TickTest"
    override val maxTicks = 1

    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Current Drift: OIL 2 with amount 50 drifted from tile 66 to tile 67.")
        assertNextLine("Current Drift: Ship 1 drifted from tile 66 to tile 67.")
    }
}
