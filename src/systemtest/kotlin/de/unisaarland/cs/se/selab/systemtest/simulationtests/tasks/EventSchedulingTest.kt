package de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * tests Scheduling of events
 * */
class EventSchedulingTest : ExampleSystemTestExtension() {
    override val description = "tests Scheduling of events"
    override val corporations = "tasksAndRewardsJsons/taskCorporation.Json"
    override val scenario = "tasksAndRewardsJsons/taskScenario.Json"
    override val map = "mapFiles/map_medium_01.json"
    override val name = "ExploreMapTaskTest"
    override val maxTicks = 6

    override suspend fun run() {
        skipUntilString("Current Drift: Ship 3 drifted from tile 65 to tile 66.")
        assertNextLine("Event: Event 15 of type RESTRICTION happened.")

        skipUntilString("Current Drift: Ship 1 drifted from tile 66 to tile 67.")
        assertNextLine("Event: Event 16 of type RESTRICTION happened.")

        skipUntilString("Current Drift: Ship 2 drifted from tile 66 to tile 67.")
        assertNextLine("Event: Event 17 of type RESTRICTION happened.")

        skipUntilString("Current Drift: Ship 2 drifted from tile 67 to tile 68.")
        assertNextLine("Event: Event 18 of type RESTRICTION happened.")

        skipUntilString("Current Drift: Ship 3 drifted from tile 66 to tile 67.")
        assertNextLine("Event: Event 19 of type RESTRICTION happened.")

        skipUntilString("Current Drift: Ship 5 drifted from tile 66 to tile 67.")
        assertNextLine("Event: Event 20 of type RESTRICTION happened.")
    }
}
