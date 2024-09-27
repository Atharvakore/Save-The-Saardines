package de.unisaarland.cs.se.selab.systemtest.collectests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * example system test
 */
class CollectTestMorePlastic : ExampleSystemTestExtension() {
    override val description = "Collect plastic only if enough ships are on tile"
    override val corporations = "collectTest/collecTestMoreShipsForPlastic.json"
    override val scenario = "collectTest/scenarioCollectTestMorePlastic.json"
    override val map = "mapFiles/smallMap1.json"
    override val name = "CollectTestMorePlastic"
    override val maxTicks = 1
    override suspend fun run() {
        assertNextLine("Initialization Info: smallMap1.json successfully parsed and validated.")
        assertNextLine("Initialization Info: collecTestMoreShipsForPlastic.json successfully parsed and validated.")
        assertNextLine("Initialization Info: scenarioCollectTestMorePlastic.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        assertNextLine("Simulation Info: Tick 0 started.")
        assertNextLine("Corporation Action: Corporation 1 is starting to move its ships.")
        assertNextLine("Ship Movement: Ship 1 moved with speed 25 to tile 14.")
        assertNextLine("Corporation Action: Corporation 1 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 2 collected 1000 of garbage OIL with 2.")
        assertNextLine("Garbage Collection: Ship 3 collected 1000 of garbage CHEMICALS with 1.")
        assertNextLine("Garbage Collection: Ship 4 collected 1000 of garbage PLASTIC with 3.")
        assertNextLine("Corporation Action: Corporation 1 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 1 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 1 finished its actions.")
        assertNextLine("Corporation Action: Corporation 2 is starting to move its ships.")
        assertNextLine("Corporation Action: Corporation 2 is starting to collect garbage.")
        assertNextLine("Garbage Collection: Ship 5 collected 1000 of garbage PLASTIC with 3.")
        assertNextLine("Garbage Collection: Ship 6 collected 500 of garbage CHEMICALS with 1.")
        assertNextLine("Garbage Collection: Ship 7 collected 1000 of garbage OIL with 5.")
        assertNextLine("Corporation Action: Corporation 2 is starting to cooperate with other corporations.")
        assertNextLine("Corporation Action: Corporation 2 is starting to refuel.")
        assertNextLine("Corporation Action: Corporation 2 finished its actions.")
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 3000 of garbage.")
        assertNextLine("Simulation Statistics: Corporation 2 collected 2500 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 2000.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 2000.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 1500.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 1500.")
    }
}
