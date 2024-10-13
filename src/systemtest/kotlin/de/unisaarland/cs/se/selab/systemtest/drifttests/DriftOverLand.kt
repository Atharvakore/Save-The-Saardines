package de.unisaarland.cs.se.selab.systemtest.drifttests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class DriftOverLand : ExampleSystemTestExtension() {
    override val description = "tests drifting a pile over land"
    override val corporations = "circleMapWithHoleInMiddle/circleMapWithHoleCorporation.json"
    override val scenario = "circleMapWithHoleInMiddle/twoPilesDriftOverLand.json"
    override val map = "circleMapWithHoleInMiddle/circleMapWithHoleAndCurrent.json"
    override val name = "drift over land test and out of boundaries"
    override val maxTicks = 1

    override suspend fun run() {
        assertNextLine("Initialization Info: circleMapWithHoleAndCurrent.json successfully parsed and validated.")
        assertNextLine("Initialization Info: circleMapWithHoleCorporation.json successfully parsed and validated.")
        assertNextLine("Initialization Info: twoPilesDriftOverLand.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        skipLines(5)
        assertNextLine("Simulation Info: Simulation ended.")
    }
}
