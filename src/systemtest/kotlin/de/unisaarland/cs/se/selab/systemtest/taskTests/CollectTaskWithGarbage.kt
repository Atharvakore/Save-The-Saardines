package de.unisaarland.cs.se.selab.systemtest.taskTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class CollectTaskWithGarbage : ExampleSystemTestExtension() {
    override val description = "tests if a collect task is performed and the reward handed out"
    override val corporations = "corporationJsons/collectTaskTestCorporations.json"
    override val scenario = "scenarioJsons/collectTaskScenario.json"
    override val map = "mapFiles/elevenTileMap.json"
    override val name = "collect task with garbage"
    override val maxTicks = 3
    override suspend fun run() {
        assertNextLine("Initialization Info: elevenTileMap.json successfully parsed and validated.")
        assertNextLine("Initialization Info: collectTaskTestCorporations.json successfully parsed and validated.")
        assertNextLine("Initialization Info: collectTaskScenario.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 6.")
        skipLines(4)
        assertNextLine("Task: Task 1 of type COLLECT with ship 1 is added with destination 1.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 1 moved with speed 40 to tile 1.")
        skipLines(4)
        assertNextLine("Reward: Task 1: Ship 2 received reward of type CONTAINER.")
        skipLines(2)
        assertNextLine("Ship Movement: Ship 1 moved with speed 20 to tile 7.")
    }
}
