package de.unisaarland.cs.se.selab.systemtest.scenarioTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * Basic 6 Tile Test. deep ocean on right with a current pushing left
 */
class RefuelInRestriction : ExampleSystemTestExtension() {
    override val description = "Check if we move out of a restriction while refueling." +
        " This test assumes we don't."
    override val corporations = "corporationJsons/basicSingleShip5.json"
    override val scenario = "scenarioJsons/yuuugeMapFarGarbage.json"
    override val map = "mapFiles/bigMap2.json"
    override val name = "UnloadInRestriction"
    override val maxTicks = 7

    override suspend fun run() {
        skipLines(39)
        assertNextLine("Event: Event 1 of type RESTRICTION happened.")
        assertNextLine("Simulation Info: Tick 5 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Refueling: Ship 1 refueled at harbor 535.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Simulation Info: Tick 6 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 484.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
    }
}
