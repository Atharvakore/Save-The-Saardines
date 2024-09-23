package de.unisaarland.cs.se.selab.systemtest.basictests.sequencetick25

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test the parsing part of tick25 sequence from spec
 * */
class SequenceTick25ParsingTest : ExampleSystemTestExtension() {
    override val description = "tests parsing part of tick25 sequence from spec"
    override val corporations = "corporationJsons/tick25Corporation.json"
    override val scenario = "scenarioJsons/tick25Scenario.json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "SequenceTick25ParsingTest"
    override val maxTicks = 1
    override suspend fun run() {
        assertLine("Initialization Info: map_medium_01.json successfully parsed and validated.", true)
        assertNextLine("Initialization Info: tick25Corporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: tick25Scenario.json successfully parsed and validated.")
    }
}
