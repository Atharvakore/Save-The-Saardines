package de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Test Sending Ships to Plastic Garbage */
class AvengersAssemble : ExampleSystemTestExtension() {
    override val corporations = "AvengersAssemble/corporation.json"
    override val description = "Move enough ships for collecting"
    override val map = "AvengersAssemble/map.json"
    override val maxTicks = 8
    override val name = "Avengers Assemble!"
    override val scenario = "AvengersAssemble/scenario.json"
    override suspend fun run() {
        skipUntilString("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 25 to tile 5.")
        assertNextLine("Ship Movement: Ship 3 moved with speed 10 to tile 5.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        skipUntilString("Simulation Info: Tick 1 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 5.")
        assertNextLine("Ship Movement: Ship 4 moved with speed 10 to tile 4.")
        skipUntilString("Simulation Info: Tick 2 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 4 moved with speed 10 to tile 5.")
        skipLines(1)
        assertNextLine("Garbage Collection: Ship 1 collected 2000 of garbage PLASTIC with 2.")
        assertNextLine("Garbage Collection: Ship 3 collected 1000 of garbage PLASTIC with 2.")
        assertNextLine("Garbage Collection: Ship 4 collected 2000 of garbage PLASTIC with 2.")
        skipUntilString("Simulation Info: Tick 3 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 2 moved with speed 25 to tile 1.")
        assertNextLine("Ship Movement: Ship 3 moved with speed 10 to tile 7.")
        skipUntilString("Simulation Info: Tick 4 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 1 moved with speed 10 to tile 7.")
        assertNextLine("Ship Movement: Ship 2 moved with speed 50 to tile 3.")
        assertNextLine("Ship Movement: Ship 4 moved with speed 10 to tile 7.")
        skipUntilString("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Unload: Ship 3 unloaded 1000 of garbage PLASTIC at harbor 7.")
        skipUntilString("Simulation Info: Tick 5 started.")
        skipLines(1)
        assertNextLine("Ship Movement: Ship 2 moved with speed 75 to tile 1.")
        skipLines(3)
        assertNextLine("Unload: Ship 1 unloaded 2000 of garbage PLASTIC at harbor 7.")
        assertNextLine("Unload: Ship 4 unloaded 2000 of garbage PLASTIC at harbor 7.")
    }
}
