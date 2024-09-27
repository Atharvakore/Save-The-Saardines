package de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test for mutants
 * */
class FindNoGarbageTest :  ExampleSystemTestExtension() {
    override val description = "tests the mutant"
    override val corporations = "mutants/FindNoGarbageCorporation.json"
    override val scenario = "mutants/FindNoGarbageScenario.json"
    override val map = "mapFiles/smallMap2.json"
    override val name = "FindNoGarbageTest"
    override val maxTicks = 2

    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Task: Task 1 of type FIND with ship 1 is added with destination 6.")

        skipUntilString("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Simulation Info: Simulation ended.")
    }
}