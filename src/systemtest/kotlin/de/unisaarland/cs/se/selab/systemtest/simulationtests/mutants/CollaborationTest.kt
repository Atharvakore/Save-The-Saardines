package de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * test for mutants
 * */
class CollaborationTest : ExampleSystemTestExtension() {
    override val description = "tests the mutant"
    override val corporations = "mutants/MultiTaskingCorporation.json"
    override val scenario = "mutants/CollaborationScenario.json"
    override val map = "mapFiles/smallMap2.json"
    override val name = "CollaborationTest"
    override val maxTicks = 1

    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
    }
}
