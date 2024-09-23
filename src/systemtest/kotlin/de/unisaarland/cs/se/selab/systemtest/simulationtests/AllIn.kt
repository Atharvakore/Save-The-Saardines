package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Drifting, Exploring, Scouting, Collecting, Returning*/
class AllIn : ExampleSystemTestExtension() {
    override val description = "All in..."
    override val corporations = "corporationJsons/smallCorporationOil.json"
    override val scenario = "scenarioJsons/allEvents.json"
    override val map = "mapFiles/smallMapWithAll.json"
    override val name = "All in..."
    override val maxTicks = 0
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMapWithAll.json successfully parsed and validated.")
        assertNextLine("Initialization Info: smallCorporationOil.json successfully parsed and validated.")
        assertNextLine("Initialization Info: allEvents.json successfully parsed and validated.")
    }
}