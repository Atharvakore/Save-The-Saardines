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
    override val name = "EventSchedulingTest"
    override val maxTicks = 17

    override suspend fun run() {
        skipUntilString("Event: Event 0 of type RESTRICTION happened.")

        skipUntilString("Event: Event 3 of type RESTRICTION happened.")

        skipUntilString("Event: Event 6 of type RESTRICTION happened.")

        skipUntilString("Event: Event 9 of type RESTRICTION happened.")

        skipUntilString("Event: Event 12 of type RESTRICTION happened.")

        skipUntilString("Event: Event 15 of type RESTRICTION happened.")
        assertNextLine("Simulation Info: Tick 16 started.")
    }
}
