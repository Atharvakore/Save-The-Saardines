package de.unisaarland.cs.se.selab.systemtest.simulationtests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test the drifting of garbage on land
 * */
class Blyat : ExampleSystemTestExtension() {
    override val description = "finds the oil mutant"
    override val corporations = "spillmutant/cor.json"
    override val scenario = "spillmutant/sce.json"
    override val map = "spillmutant/map.json"
    override val name = "blablabla"
    override val maxTicks = 5
    override suspend fun run() {
        skipUntilString("Simulation Statistics: Corporation 0 collected 1000 of garbage.")
        skipUntilString("Simulation Statistics: Total amount of oil collected: 1000.")
        skipLines(1)
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
    }
}
