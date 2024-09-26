package de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test moving to tile with garbage from cooperating info
 * */
class CoordinatingShipDefaultTest : ExampleSystemTestExtension() {
    override val description = "test moving to tile with garbage from cooperating info"
    override val corporations = "mutants/CoordinatingShipDefaultCorporation.Json"
    override val scenario = "mutants/CoordinatingShipDefaultScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CoordinatingShipDefaultTest"
    override val maxTicks = 1

    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to move its ships.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 5 moved with speed 10 to tile 25.")
    }
}
