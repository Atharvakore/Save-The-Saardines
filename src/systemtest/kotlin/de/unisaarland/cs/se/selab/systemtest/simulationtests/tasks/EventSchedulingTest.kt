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
    override val maxTicks = 6

    override suspend fun run() {
        skipUntilString("Event: Event 15 of type RESTRICTION happened.")

        skipUntilString("Event: Event 16 of type RESTRICTION happened.")

        skipUntilString("Event: Event 17 of type RESTRICTION happened.")

        skipUntilString("Event: Event 18 of type RESTRICTION happened.")

        skipUntilString("Event: Event 19 of type RESTRICTION happened.")

        skipUntilString("Event: Event 20 of type RESTRICTION happened.")

        skipUntilString("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
    }
}
