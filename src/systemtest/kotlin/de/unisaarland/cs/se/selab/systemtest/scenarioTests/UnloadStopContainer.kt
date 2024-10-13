package de.unisaarland.cs.se.selab.systemtest.scenarioTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

const val CORP1MOVE = "Corporation Action: Corporation 1 is starting to move its ships."
const val CORP1GARBO = "Corporation Action: Corporation 1 is starting to collect garbage."
const val CORP1COOP = "Corporation Action: Corporation 1 is starting to cooperate with other corporations."
const val CORP1REFUEL = "Corporation Action: Corporation 1 is starting to refuel."
const val CORP1FINISH = "Corporation Action: Corporation 1 finished its actions."
const val SIMULATION_STARTED = "Simulation Info: Simulation started."
const val TICK_STARTED = "Simulation Info: Tick "
const val SHIP_MOVEMENT = "Ship Movement: "
const val TASK_ADDED = "Task: "
const val REWARD = "Reward: "
const val GARBAGE_COLLECTION = "Garbage Collection: "

/**
 * Basic 6 Tile Test. deep ocean on right with a current pushing left
 */
class UnloadStopContainer : ExampleSystemTestExtension() {
    override val description = "Check if unload if container given"
    override val corporations = "corporationJsons/basicSingleShip3.json"
    override val scenario = "scenarioJsons/bigPileOneRewardScenario.json"
    override val map = "mapFiles/mediumMapNoCurrents.json"
    override val name = "UnloadStopContainer"
    override val maxTicks = 10

    override suspend fun run() {
        assertNextLine(
            "Initialization Info: mediumMapNoCurrents.json success" +
                "fully parsed and validated."
        )
        assertNextLine(
            "Initialization Info: basicSingleShip3.json " +
                "successfully parsed and validated."
        )
        assertNextLine(
            "Initialization Info: bigPileOneRewardScenario.json " +
                "successfully parsed and validated."
        )
        assertNextLine(SIMULATION_STARTED)
        assertNextLine(TICK_STARTED + "0 started.")
        assertNextLine(CORP1MOVE)
        assertNextLine(SHIP_MOVEMENT + "Ship 1 moved with speed 25 to tile 22.")
        assertNextLine(SHIP_MOVEMENT + "Ship 63 moved with speed 10 to tile 7.")
        assertNextLine(CORP1GARBO)
        assertNextLine(CORP1COOP)
        assertNextLine(CORP1REFUEL)
        assertNextLine(CORP1FINISH)
        assertNextLine(TASK_ADDED + "Task 1 of type EXPLORE with ship 63 is added with destination 11.")
        assertNextLine(TICK_STARTED + "1 started.")
        assertNextLine(CORP1MOVE)
        assertNextLine(SHIP_MOVEMENT + "Ship 1 moved with speed 50 to tile 37.")
        assertNextLine(SHIP_MOVEMENT + "Ship 63 moved with speed 20 to tile 11.")
        assertNextLine(CORP1GARBO)
        assertNextLine(CORP1COOP)
        assertNextLine(CORP1REFUEL)
        assertNextLine(CORP1FINISH)
        assertNextLine(REWARD + "Task 1: Ship 63 received reward of type TELESCOPE.")
        assertNextLine(TASK_ADDED + "Task 2 of type EXPLORE with ship 63 is added with destination 11.")
        assertNextLine(TICK_STARTED + "2 started.")
        assertNextLine(CORP1MOVE)
        assertNextLine(CORP1GARBO)
        assertNextLine(CORP1COOP)
        assertNextLine(CORP1REFUEL)
        assertNextLine(CORP1FINISH)
        assertNextLine(REWARD + "Task 2: Ship 63 received reward of type TELESCOPE.")
        assertNextLine(TICK_STARTED + "3 started.")
        assertNextLine(CORP1MOVE)
        assertNextLine(SHIP_MOVEMENT + "Ship 63 moved with speed 10 to tile 7.")
        assertNextLine(CORP1GARBO)
        assertNextLine(CORP1COOP)
        assertNextLine(CORP1REFUEL)
        assertNextLine(CORP1FINISH)
        assertNextLine(TICK_STARTED + "4 started.")
        assertNextLine(CORP1MOVE)
        assertNextLine(SHIP_MOVEMENT + "Ship 63 moved with speed 20 to tile 24.")
        assertNextLine(CORP1GARBO)
        assertNextLine(CORP1COOP)
        assertNextLine(CORP1REFUEL)
        assertNextLine(CORP1FINISH)
        assertNextLine(TICK_STARTED + "5 started.")
        assertNextLine(CORP1MOVE)
        assertNextLine(SHIP_MOVEMENT + "Ship 63 moved with speed 30 to tile 37.")
        run2()
    }

    private suspend fun run2() {
        assertNextLine(CORP1GARBO)
        assertNextLine(GARBAGE_COLLECTION + "Ship 63 collected 3000 of garbage PLASTIC with 1.")
        assertNextLine(CORP1COOP)
        assertNextLine(CORP1REFUEL)
        assertNextLine(CORP1FINISH)
        assertNextLine(TASK_ADDED + "Task 5 of type COLLECT with ship 1 is added with destination 32.")
        assertNextLine(TICK_STARTED + "6 started.")
        assertNextLine(CORP1MOVE)
        assertNextLine(SHIP_MOVEMENT + "Ship 1 moved with speed 25 to tile 32.")
        assertNextLine(SHIP_MOVEMENT + "Ship 63 moved with speed 10 to tile 24.")
        assertNextLine(CORP1GARBO)
        assertNextLine(CORP1COOP)
        assertNextLine(CORP1REFUEL)
        assertNextLine(CORP1FINISH)
        assertNextLine(REWARD + "Task 5: Ship 63 received reward of type CONTAINER.")
        assertNextLine(TICK_STARTED + "7 started.")
        assertNextLine(CORP1MOVE)
        assertNextLine(SHIP_MOVEMENT + "Ship 1 moved with speed 25 to tile 36.")
        assertNextLine(SHIP_MOVEMENT + "Ship 63 moved with speed 20 to tile 36.")
        assertNextLine(CORP1GARBO)
        assertNextLine(GARBAGE_COLLECTION + "Ship 63 collected 3000 of garbage PLASTIC with 2.")
        assertNextLine(CORP1COOP)
        assertNextLine(CORP1REFUEL)
        assertNextLine(CORP1FINISH)
        assertNextLine(TICK_STARTED + "8 started.")
        assertNextLine(CORP1MOVE)
        assertNextLine(SHIP_MOVEMENT + "Ship 1 moved with speed 25 to tile 3.")
        assertNextLine(CORP1GARBO)
        assertNextLine(CORP1COOP)
        assertNextLine(CORP1REFUEL)
        assertNextLine(CORP1FINISH)
        assertNextLine(TICK_STARTED + "9 started.")
        assertNextLine(CORP1MOVE)
        assertNextLine(SHIP_MOVEMENT + "Ship 1 moved with speed 50 to tile 11.")
        assertNextLine(CORP1GARBO)
        assertNextLine(CORP1COOP)
        assertNextLine(CORP1REFUEL)
        assertNextLine(CORP1FINISH)
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation 1 collected 6000 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 6000.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 0.")
    }
}
