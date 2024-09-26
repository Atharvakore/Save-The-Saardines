package de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test not moving collecting ships when no garbage visible
 * */
class CorpNoMove2Test : ExampleSystemTestExtension() {
    override val description = "test not moving collecting ships when no garbage visible"
    override val corporations = "mutants/CorpNoMove2Corporation.Json"
    override val scenario = "mutants/CorpNoMove1Scenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CorpNoMove2Test"
    override val maxTicks = 1

    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
    }
}
