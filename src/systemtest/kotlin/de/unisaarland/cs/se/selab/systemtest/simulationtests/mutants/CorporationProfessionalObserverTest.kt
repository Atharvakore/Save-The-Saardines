package de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests for corporation visibility range
 * */
class CorporationProfessionalObserverTest : ExampleSystemTestExtension() {
    override val description = "tests for corporation visibility range for SCOUTING ship"
    override val corporations = "mutants/ProfessionalObserverCorporation.Json"
    override val scenario = "mutants/ProfessionalObserverScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "CorporationProfessionalObserverTest"
    override val maxTicks = 1

    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to move its ships.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 4 moved with speed 10 to tile 64.")
    }
}
