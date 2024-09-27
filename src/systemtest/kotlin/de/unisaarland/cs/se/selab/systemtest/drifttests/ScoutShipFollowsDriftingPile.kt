package de.unisaarland.cs.se.selab.systemtest.drifttests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class ScoutShipFollowsDriftingPile : ExampleSystemTestExtension() {
    override val description = "tests if a scout ship follows a drifting garbage pile"
    override val corporations = "circleMapWithHoleInMiddle/scoutingShipFollowsPileCorporations.json"
    override val scenario = "circleMapWithHoleInMiddle/singleOilPile.json"
    override val map = "circleMapWithHoleInMiddle/circleMap.json"
    override val name = "correct order of pick up of multiple piles on a single tile"
    override val maxTicks = 3

    override suspend fun run() {
        assertNextLine("Initialization Info: circleMap.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: scoutingShipFollowsPileCorporations.json" +
                " successfully parsed and validated."
        )
        assertNextLine("Initialization Info: singleOilPile.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 2 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 2.")
        skipLines(4)
        assertNextLine("Current Drift: OIL 1 with amount 50 drifted from tile 3 to tile 4.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 3.")
        skipLines(4)
        assertNextLine("Current Drift: OIL 1 with amount 50 drifted from tile 4 to tile 5.")
        assertNextLine("Current Drift: Ship 2 drifted from tile 3 to tile 4.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 2 moved with speed 10 to tile 5.")
        skipLines(4)
        assertNextLine("Current Drift: OIL 1 with amount 50 drifted from tile 5 to tile 6.")
        assertNextLine("Current Drift: Ship 2 drifted from tile 5 to tile 6.")
    }
}
