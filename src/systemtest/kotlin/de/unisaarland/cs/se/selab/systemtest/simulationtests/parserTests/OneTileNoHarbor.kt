package de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * mutant test
 */
class OneTileNoHarbor : ExampleSystemTestExtension() {
    override val description = "map with one tile not harbor"
    override val corporations = "corporationJsons/corpNoHarbor.json"
    override val scenario = "scenarioJsons/scenario.json"
    override val map = "mapFiles/oneTileNotHarbor.json"
    override val name = "MapNoHarbor"
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: oneTileNotHarbor.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpNoHarbor.json is invalid.")
    }
}
