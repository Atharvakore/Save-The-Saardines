package de.unisaarland.cs.se.selab.systemtest.movetests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class SlowAcceleration : ExampleSystemTestExtension() {
    override val description = "tests ships with an acceleration of < 10"
    override val corporations = "corporationJsons/slowShipCorporations.json"
    override val scenario = "scenarioJsons/multiplePlasticPilesScenario.json"
    override val map = "mapFiles/elevenTileMap.json"
    override val name = "slow acceleration"
    override val maxTicks = 2

    override suspend fun run() {
        assertNextLine("Initialization Info: elevenTileMap.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: slowShipCorporations.json" +
                " successfully parsed and validated."
        )
        assertNextLine("Initialization Info: multiplePlasticPilesScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        skipLines(7)
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 5.")
    }
}
