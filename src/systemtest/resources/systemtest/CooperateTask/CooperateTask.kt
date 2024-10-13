package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import kotlin.math.min

/**
 * This sysTest checks whether a restriction occurs correctly
 * */
class CooperateTask : ExampleSystemTestExtension() {

    override val description = "tests whether corporations cooperate correctly via Task"
    override val corporations = "CooperateTaskConfig/TwoCorpThreeShips.json"
    override val scenario = "CooperateTaskConfig/1GarbageAndCooperateTask.json"
    override val map = "CooperateTaskConfig/mapShallowAndDeep.json"
    override val name = "Cooperating Task"
    override val maxTicks = 3

    val corporation1Id = 1
    val corporation2Id = 2
    val scoutShipIDCopr1 = 1
    val scoutShipAccelerationCorp1 = 20
    var scoutingShipSpeedCrop1 = 0
    val scoutingMaxSpeedCrop1 = 20
    val collectingShipIDCrop1 = 2
    val collectingShipAccelerationCrop1 = 10
    var collectingShipSpeedCrop1 = 0
    val collectingMaxSpeedCrop1 = 10

    override suspend fun run() {
        var tick = 0
        // Initial Setup
        initializationAssertion()
        // beginning of tick 0

        startTick(tick)
        // corporation actions started

        startCoprMovement(corporation1Id)
        // corporation movement
        // Scouting ship with id 1 moves toward garbage
        // currentVelocity == 0
        // Go to 13(2,1)
        scoutingShipSpeedCrop1 = min(scoutingShipSpeedCrop1 + scoutShipAccelerationCorp1, scoutingMaxSpeedCrop1)
        assertNextLine("Ship Movement: Ship $scoutShipIDCopr1 moved with speed $scoutingShipSpeedCrop1 to tile 22.")
        // collecting ship moves toward garbage in VR of corp
        // currentVelocity == 0
        collectingShipSpeedCrop1 = min(
            collectingShipSpeedCrop1 + collectingShipAccelerationCrop1,
            collectingMaxSpeedCrop1
        )
        // move towards tile 2 (1,0)
        assertNextLine(
            "Ship Movement: Ship $collectingShipIDCrop1 moved with speed $collectingShipSpeedCrop1 to tile 12."
        )
        // end movement
        noMovementForCorp1()

        /** Corporation 2
         * */
        corpNoAction()
        // no drift no event
        // Task assignement
        assertNextLine("Task: Task 1 of type COOPERATE with ship 1 is added with destination 13.")
        tick += 1

        // end of tick 0

        /**
         * begin of the tick 1
         * */

        startTick(tick)

        startCoprMovement(corporation1Id)
        // corporation movement
        // Scouting ship with id 1 moves toward garbage
        // currentVelocity == 0
        // Go to 13(2,1)
        scoutingShipSpeedCrop1 = min(scoutingShipSpeedCrop1 + scoutShipAccelerationCorp1, scoutingMaxSpeedCrop1)
        assertNextLine("Ship Movement: Ship $scoutShipIDCopr1 moved with speed $scoutingShipSpeedCrop1 to tile 13.")
        // it reaches the destination
        scoutingShipSpeedCrop1 = 0
        // currentVelocity == 0
        collectingShipSpeedCrop1 = min(
            collectingShipSpeedCrop1 + collectingShipAccelerationCrop1,
            collectingMaxSpeedCrop1
        )
        // move towards tile 2 (1,0)
        assertNextLine(
            "Ship Movement: Ship $collectingShipIDCrop1 moved with speed $collectingShipSpeedCrop1 to tile 22."
        )
        // end movement
        noMovementForCorp1()

        /** Corporation 2
         * */
        corpNoAction()

        tick += 1
        // end of tick 1
        /** Begin of tick 2
         * */
        startTick(tick)

        startCoprMovement(corporation1Id)

        collectingShipSpeedCrop1 = min(
            collectingShipSpeedCrop1 + collectingShipAccelerationCrop1,
            collectingMaxSpeedCrop1
        )
        // move towards tile 2 (1,0)
        assertNextLine(
            "Ship Movement: Ship $collectingShipIDCrop1 moved with speed $collectingShipSpeedCrop1 to tile 33."
        )
        // end movement
        noMovementForCorp1()

        /** Corporation 2
         * */
        startCoprMovement(corporation2Id)
        // end movement
        skipLines(2)
        startCorpCollecting(corporation2Id)
        // no collect

        corpStartedCooperation(corporation2Id)
        // no cooperation
        corpStartRefuel(corporation2Id)
        corpFinishAction(corporation2Id)
        assertNextLine("Reward: Task 1: Ship 1 received reward of type RADIO.")
        tick += 1
        // end of tick 2

        simulationStatsAssertion(corporation1Id, corporation2Id)
    }

    private suspend fun CooperateTask.noMovementForCorp1() {
        startCorpCollecting(corporation1Id)
        corpStartedCooperation(corporation1Id)
        corpStartRefuel(corporation1Id)
        corpFinishAction(corporation1Id)
    }

    private suspend fun CooperateTask.corpNoAction() {
        startCoprMovement(corporation2Id)
        // end movement
        skipLines(1)
        startCorpCollecting(corporation2Id)
        // no collect

        corpStartedCooperation(corporation2Id)
        // no cooperation
        corpStartRefuel(corporation2Id)
        corpFinishAction(corporation2Id)
    }

    private suspend fun CooperateTask.corpFinishAction(corporationId: Int) {
        val expectedCorpActionFinish = "Corporation Action: Corporation $corporationId finished its actions."
        assertNextLine(expectedCorpActionFinish)
    }

    private suspend fun CooperateTask.startCoprMovement(corporationId: Int) {
        val expectedCoprMoementStart = "Corporation Action: Corporation $corporationId is starting to move its ships."
        assertNextLine(expectedCoprMoementStart)
    }

    private suspend fun startCorpCollecting(corpID: Int) {
        val expectedCorpCollection = "Corporation Action: Corporation $corpID is starting to collect garbage."
        assertNextLine(expectedCorpCollection)
    }

    private suspend fun startTick(tick: Int) {
        val expectedTickStarted = "Simulation Info: Tick $tick started."
        assertNextLine(expectedTickStarted)
    }

    private suspend fun corpStartedCooperation(
        corporationId: Int
    ) {
        val expectedCorpCooperation =
            "Corporation Action: Corporation $corporationId is starting to cooperate with other corporations."
        assertNextLine(
            expectedCorpCooperation
        )
    }

    private suspend fun CooperateTask.corpStartRefuel(
        corporationId: Int
    ) {
        val expectedCorpRefeulStart = "Corporation Action: Corporation $corporationId is starting to refuel."
        assertNextLine(expectedCorpRefeulStart)
    }

    private suspend fun CooperateTask.initializationAssertion() {
        assertNextLine("Initialization Info: mapShallowAndDeep.json successfully parsed and validated.")
        assertNextLine("Initialization Info: TwoCorpThreeShips.json successfully parsed and validated.")
        assertNextLine("Initialization Info: 1GarbageAndCooperateTask.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
    }

    private suspend fun CooperateTask.simulationStatsAssertion(corporation1Id: Int, corporation2Id: Int) {
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation $corporation1Id collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Corporation $corporation2Id collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 1000.")
    }
}
