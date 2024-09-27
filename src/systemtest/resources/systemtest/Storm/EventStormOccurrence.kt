package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import kotlin.math.min

/**
 * This sysTest checks whether a restriction occurs correctly
 * */
class EventStormOccurrence : ExampleSystemTestExtension() {

    override val description = "tests whether Garbage drift via Storm correctly"
    override val corporations = "OilEventConfigs/OneCorpTwoShips.json"
    override val scenario = "OilEventConfigs/1GarbageAndStorm.json"
    override val map = "OilEventConfigs/mapShallowAndDeep.json"
    override val name = "DoesStormDriftTheGarbage"
    override val maxTicks = 3

    val corporationId = 1
    val scoutShipID = 1
    val scoutShipAcceleration = 25
    var scoutingShipSpeed = 0
    val collectingShipID = 2
    val collectingShipAcceleration = 10
    var collectingShipSpeed = 0
    val scoutingMaxSpeed = 100
    val collectingMaxSpeed = 10

    override suspend fun run() {
        var tick = 0
        // Initial Setup
        initializationAssertion()
        // beginning of tick 0

        startTick(tick)
        // corporation actions started

        startCoprMovement(corporationId)
        // corporation movement
        // Scouting ship with id 1 moves toward garbage
        // currentVelocity == 0
        // it starts moving from tile 0 to tile 5(2,1) with vel 25
        scoutingShipSpeed = min(scoutingShipSpeed + scoutShipAcceleration, scoutingMaxSpeed)
        assertNextLine("Ship Movement: Ship $scoutShipID moved with speed $scoutingShipSpeed to tile 5.")
        // collecting ship moves toward garbage in VR of corp
        // currentVelocity == 0
        // it starts moving from tile 0 to tile 2(1,0) with vel 10
        collectingShipSpeed = min(collectingShipSpeed + collectingShipAcceleration, collectingMaxSpeed)
        assertNextLine("Ship Movement: Ship $collectingShipID moved with speed $collectingShipSpeed to tile 2.")
        // end movement

        startCorpCollecting(corporationId)
        // no collect

        corpStartedCooperation(corporationId)
        // no cooperation

        corpStartRefuel(corporationId)
        // no refueling no unloading  needed

        corpFinishAction(corporationId)
        tick += 1

        // end of tick 0

        /**
         * begin of the tick 1
         * */

        startTick(tick)

        // corporation actions started
        startCoprMovement(corporationId)

        // it starts moving from tile 5(2,1) to tile 6(2,2) with vel 50
        scoutingShipSpeed = min(scoutingShipSpeed + scoutShipAcceleration, scoutingMaxSpeed)
        assertNextLine("Ship Movement: Ship $scoutShipID moved with speed $scoutingShipSpeed to tile 6.")
        // Ship decelerate
        scoutingShipSpeed = 0
        // collecting ship moves toward garbage in VR of corp
        // currentVelocity == 0
        // it starts moving from tile 2(1,0) to tile 5(2,1) with vel 10
        collectingShipSpeed = min(collectingShipSpeed + collectingShipAcceleration, collectingMaxSpeed)
        assertNextLine("Ship Movement: Ship $collectingShipID moved with speed $collectingShipSpeed to tile 5.")
        // end movement
        // Collecting started
        startCorpCollecting(corporationId)
        // since no ships on tiles with garbage go to next phase
        // begin cooperation
        // no collect

        corpStartedCooperation(corporationId)
        // no cooperation

        corpStartRefuel(corporationId)
        // no refueling no unloading  needed

        corpFinishAction(corporationId)

        assertNextLine("Event: Event 1 of type STORM happened.")

        tick += 1

        /**
         * End of tick 1
         * */

        /**
         * begin of the tick 2
         * */

        startTick(tick)
        // corporation actions started
        startCoprMovement(corporationId)

        // currentVelocity == 0
        // it starts moving from tile 6(2,2) to tile 7(2,3) with vel 25
        scoutingShipSpeed = min(scoutingShipSpeed + scoutShipAcceleration, scoutingMaxSpeed)
        assertNextLine("Ship Movement: Ship $scoutShipID moved with speed $scoutingShipSpeed to tile 7.")
        // collecting ship moves toward garbage in VR of corp
        // currentVelocity == 0
        // it starts moving from tile 3(1,1) to tile 4(1,2) with vel 10
        collectingShipSpeed = min(collectingShipSpeed + collectingShipAcceleration, collectingMaxSpeed)
        assertNextLine("Ship Movement: Ship $collectingShipID moved with speed $collectingShipSpeed to tile 4.")
        // end movement
        // Collecting started
        startCorpCollecting(corporationId)
        // since no ships on tiles with garbage go to next phase
        // begin cooperation
        corpStartedCooperation(corporationId)
        // since no other corporations move on to next phase
        // begin refueling
        corpStartRefuel(corporationId)
        // no refueling no unloading  needed

        corpFinishAction(corporationId)

        assertNextLine("Event: Event 2 of type STORM happened.")
        tick += 1
        /**
         * End of tick 2
         * */
        // end of simulation
        simulationStatsAssertion(corporationId)
    }

    private suspend fun EventStormOccurrence.corpFinishAction(corporationId: Int) {
        val expectedCorpActionFinish = "Corporation Action: Corporation $corporationId finished its actions."
        assertNextLine(expectedCorpActionFinish)
    }

    private suspend fun EventStormOccurrence.startCoprMovement(corporationId: Int) {
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

    private suspend fun EventStormOccurrence.corpStartRefuel(
        corporationId: Int
    ) {
        val expectedCorpRefeulStart = "Corporation Action: Corporation $corporationId is starting to refuel."
        assertNextLine(expectedCorpRefeulStart)
    }

    private suspend fun EventStormOccurrence.initializationAssertion() {
        assertNextLine("Initialization Info: mapShallowAndDeep.json successfully parsed and validated.")
        assertNextLine("Initialization Info: OneCorpTwoShips.json successfully parsed and validated.")
        assertNextLine("Initialization Info: 1GarbageAndStorm.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
    }

    private suspend fun EventStormOccurrence.simulationStatsAssertion(corporationId: Int) {
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation $corporationId collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 1000.")
    }
}
