package de.unisaarland.cs.se.selab.systemtest.taskTest

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 *
 */
class FindTaskWithNoGarbage : ExampleSystemTestExtension() {
    override val description = "tests if a find task with target Tile no garbage on " +
        "it so should complete the task but no reward"
    override val corporations = "corporationJsons/corpFindTask.json"
    override val scenario = "scenarioJsons/findTaskTest.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "find task with no garbage"
    override val maxTicks = 2
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: corpFindTask.json successfully parsed and validated.")
        assertNextLine("Initialization Info: findTaskTest.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 3.")
        skipLines(4)
        assertNextLine("Task: Task 1 of type FIND with ship 1 is added with destination 3.")
        skipLines(1)
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        skipLines(4)
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
    }
}
