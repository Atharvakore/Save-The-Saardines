package de.unisaarland.cs.se.selab.systemtest.drifttests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class CircleDriftTest : ExampleSystemTestExtension() {
    override val description = "tests drifting a larger pile than the current can carry"
    override val corporations = "circleMapWithHoleInMiddle/circleMapWithHoleCorporation.json"
    override val scenario = "circleMapWithHoleInMiddle/bigOilPile.json"
    override val map = "circleMapWithHoleInMiddle/circleMap.json"
    override val name = "circle drift test"
    override val maxTicks = 6

    override suspend fun run() {
        assertNextLine("Initialization Info: circleMap.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: circleMapWithHoleCorporation.json" +
                " successfully parsed and validated."
        )
        assertNextLine("Initialization Info: bigOilPile.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        skipLines(5)
        assertNextLine("Current Drift: OIL 2 with amount 50 drifted from tile 3 to tile 4.")
        skipLines(6)
        assertNextLine("Current Drift: OIL 3 with amount 50 drifted from tile 3 to tile 4.")
        assertNextLine("Current Drift: OIL 2 with amount 50 drifted from tile 4 to tile 5.")
        skipLines(6)
        assertNextLine("Current Drift: OIL 4 with amount 50 drifted from tile 3 to tile 4.")
        assertNextLine("Current Drift: OIL 3 with amount 50 drifted from tile 4 to tile 5.")
        assertNextLine("Current Drift: OIL 2 with amount 50 drifted from tile 5 to tile 6.")
        skipLines(6)
        assertNextLine("Current Drift: OIL 5 with amount 50 drifted from tile 3 to tile 4.")
        assertNextLine("Current Drift: OIL 4 with amount 50 drifted from tile 4 to tile 5.")
        assertNextLine("Current Drift: OIL 3 with amount 50 drifted from tile 5 to tile 6.")
        assertNextLine("Current Drift: OIL 2 with amount 50 drifted from tile 6 to tile 7.")
        skipLines(6)
        assertNextLine("Current Drift: OIL 6 with amount 50 drifted from tile 3 to tile 4.")
        assertNextLine("Current Drift: OIL 5 with amount 50 drifted from tile 4 to tile 5.")
        assertNextLine("Current Drift: OIL 4 with amount 50 drifted from tile 5 to tile 6.")
        assertNextLine("Current Drift: OIL 3 with amount 50 drifted from tile 6 to tile 7.")
        assertNextLine("Current Drift: OIL 2 with amount 50 drifted from tile 7 to tile 8.")
        skipLines(6)
        assertNextLine("Current Drift: OIL 1 with amount 50 drifted from tile 3 to tile 4.")
        assertNextLine("Current Drift: OIL 6 with amount 50 drifted from tile 4 to tile 5.")
        assertNextLine("Current Drift: OIL 5 with amount 50 drifted from tile 5 to tile 6.")
        assertNextLine("Current Drift: OIL 4 with amount 50 drifted from tile 6 to tile 7.")
        assertNextLine("Current Drift: OIL 3 with amount 50 drifted from tile 7 to tile 8.")
        assertNextLine("Current Drift: OIL 2 with amount 50 drifted from tile 8 to tile 3.")
    }
}
