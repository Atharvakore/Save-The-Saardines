package de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
/** Check Scouting ship exploring */
class ExploringPacific : ExampleSystemTestExtension() {
    override val description = "tests Scouting ship exploring"
    override val corporations = "ExploringPacific/coprorations.json"
    override val scenario = "ExploringPacific/scenario.json"
    override val map = "ExploringPacific/map.json"
    override val name = "ExploringPacific"
    override val maxTicks = 17

    override suspend fun run() {
        skipUntilString("Simulation Info: Simulation started.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 5.")
        skipLines(6)
        moveScoutingShip()
        skipLines(6)
        moveScoutingShip()
        skipLines(6)
        moveScoutingShip()
        skipLines(6)
        moveScoutingShip()
        skipLines(6)
        moveScoutingShip()
        skipLines(6)
        moveScoutingShip()
        skipLines(6)
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 13.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 16.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 9.")
        skipLines(2)
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        skipLines(6)
        assertNextLine("Refueling: Ship 1 refueled at harbor 9.")
        skipLines(1)
    }

    private suspend fun moveScoutingShip() {
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 3.")
        skipLines(6)
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 1.")
    }
}
