package de.unisaarland.cs.se.selab.systemtest.simulationtests.unloading

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test collecting ship
 */
class WhereDoWeUnloadThis : ExampleSystemTestExtension() {
    override val description = "Test collecting ship."
    override val corporations = "WhereDoWeUnloadThis/corporations.json"
    override val scenario = "WhereDoWeUnloadThis/scenario.json"
    override val map = "WhereDoWeUnloadThis/map.json"
    override val name = "WhereDoWeUnloadThis"
    override val maxTicks = 1

    override suspend fun run() {
        skipUntilString("Simulation Info: Simulation started.")
        skipLines(3)
        assertNextLine("Garbage Collection: Ship 1 collected 1000 of garbage PLASTIC with 1.")
        skipLines(5)
        assertNextLine("Simulation Statistics: Corporation 1 collected 1000 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 1000.")
        skipLines(3)
        assertEnd()
    }
}
