package de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests tasks in restriction area
 * */
class TaskInRestrictTest : ExampleSystemTestExtension() {
    override val description = "tests tasks in restriction area"
    override val corporations = "mutants/taskInRestrictCorporation.json"
    override val scenario = "mutants/taskInRestrictScenario.json"
    override val map = "mapFiles/smallMap.json"
    override val name = "TaskInRestrictTest"
    override val maxTicks = 2

    override suspend fun run() {
        skipUntilString("Current Drift: OIL 2 with amount 150 drifted from tile 5 to tile 3.")
        assertNextLine("Event: Event 2 of type RESTRICTION happened.")
        assertNextLine("Task: Task 3 of type COLLECT with ship 1 is added with destination 8.")

        skipUntilString("Current Drift: OIL 3 with amount 150 drifted from tile 5 to tile 3.")
        assertNextLine("Simulation Info: Simulation ended.")
    }
}
