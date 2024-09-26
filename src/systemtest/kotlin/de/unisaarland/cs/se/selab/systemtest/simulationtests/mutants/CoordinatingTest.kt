package de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test moving to tile from coordinating
 * */
class CoordinatingTest : ExampleSystemTestExtension() {
    override val description = "test moving to tile from coordinating"
    override val corporations = "mutants/CoordinatingCorporation.Json"
    override val scenario = "mutants/CoordinatingScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CoordinatingTest"
    override val maxTicks = 2

    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 5 moved with speed 10 to tile 25.")

        skipUntilString("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 5 moved with speed 20 to tile 26.")
    }
}
