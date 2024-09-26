package de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test cooperating only once
 * */
class CooperateOnceTest : ExampleSystemTestExtension() {
    override val description = "test cooperating only once"
    override val corporations = "mutants/CooperateOnceCorporation.Json"
    override val scenario = "mutants/CooperateOnceScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CooperateOnceTest"
    override val maxTicks = 2

    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 5 moved with speed 10 to tile 25.")

        skipUntilString("Corporation Action: Corporation 1 is starting to move its ships.")
        skipUntilString("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
    }
}
