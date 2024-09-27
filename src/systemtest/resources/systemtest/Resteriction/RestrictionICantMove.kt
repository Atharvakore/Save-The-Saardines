package de.unisaarland.cs.se.selab.systemtest.basictests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
import kotlin.math.min

/**
 * This sysTest checks whether a restriction occurs correctly
 * */
class RestrictionICantMove : ExampleSystemTestExtension() {

    override val description = "tests whether ships cant move due to Restriction"
    override val corporations = "RestrictionEventCantMove/OneCorpTwoShips.json"
    override val scenario = "RestrictionEventCantMove/1GarbageAndRestriction.json"
    override val map = "RestrictionEventCantMove/mapShallowAndDeep.json"
    override val name = "RestrictionICantMove"
    override val maxTicks = 6

    val corporationId = 1
    val scoutShipID = 1
    val scoutShipAcceleration = 10
    var scoutingShipSpeed = 0
    val collectingShipID = 2
    val collectingShipAcceleration = 10
    var collectingShipSpeed = 0
    val scoutingMaxSpeed = 10
    val collectingMaxSpeed = 10

    val expectedCoprMoementStart = "Corporation Action: Corporation $corporationId is starting to move its ships."
    val expectedCorpCollection = "Corporation Action: Corporation $corporationId is starting to collect garbage."
    val expectedCorpActionFinish = "Corporation Action: Corporation $corporationId finished its actions."

    override suspend fun run() {
        var tick = 0
        // Initial Setup
        initializationAssertion()

        val expectedCorpRefeulStart = "Corporation Action: Corporation $corporationId is starting to refuel."
        // beginning of tick 0

        startTick(tick)
        // corporation actions started

        assertNextLine(expectedCoprMoementStart)
        // corporation movement
        // Scouting ship with id 1 moves toward garbage
        // currentVelocity == 0
        // it starts moving from tile 0 to tile 5(2,1) with vel 25
        scoutingShipSpeed = min(scoutingShipSpeed + scoutShipAcceleration, scoutingMaxSpeed)
        assertNextLine("Ship Movement: Ship $scoutShipID moved with speed $scoutingShipSpeed to tile 12.")
        // collecting ship moves toward garbage in VR of corp
        // currentVelocity == 0
        // it starts moving from tile 0 to tile 2(1,0) with vel 10
        collectingShipSpeed = min(collectingShipSpeed + collectingShipAcceleration, collectingMaxSpeed)
        assertNextLine("Ship Movement: Ship $collectingShipID moved with speed $collectingShipSpeed to tile 12.")
        startCorpCollecting(corporationId)
        corpStartedCooperation(corporationId)
        corpStartRefuel(corporationId)
        corpFinishAction(corporationId)
        // no Drift due to Storm or Current. Go to event handling
        // Only one Restriction event
        assertNextLine("Event: Event 1 of type RESTRICTION happened.")
        tick += 1
        // end of simulation
        /**
         * begin of the tick 0
         * */
        // Five Ticks without any actions
        boringTicks(5, scoutShipID, scoutingShipSpeed)
        tick += 5
        /**
         * Begin of  of tick 6
         * */
        // end of simulation
        simulationStatsAssertion(corporationId)
    }

    private suspend fun boringTicks(ticks: Int, scoutShipID: Int, scoutingShipSpeed: Int) {
        for (i in 1..ticks) {
            startTick(i)
            assertNextLine(expectedCoprMoementStart)
            if (i % 2 == 0) {
                assertNextLine("Ship Movement: Ship $scoutShipID moved with speed $scoutingShipSpeed to tile 12.")
            } else {
                assertNextLine("Ship Movement: Ship $scoutShipID moved with speed $scoutingShipSpeed to tile 1.")
            }

            startCorpCollecting(corporationId)
            corpStartedCooperation(corporationId)
            corpStartRefuel(corporationId)
            corpFinishAction(corporationId)
            // no events whatSoEver
        }
    }

    private suspend fun corpFinishAction(corporationId: Int) {
        val expectedCorpActionFinish = "Corporation Action: Corporation $corporationId finished its actions."
        assertNextLine(expectedCorpActionFinish)
    }
    private suspend fun corpStartRefuel(
        corporationId: Int
    ) {
        val expectedCorpRefeulStart = "Corporation Action: Corporation $corporationId is starting to refuel."
        assertNextLine(expectedCorpRefeulStart)
    }

    private suspend fun startTick(tick: Int) {
        val expectedTickStarted = "Simulation Info: Tick $tick started."
        assertNextLine(expectedTickStarted)
    }
    private suspend fun startCorpCollecting(corpID: Int) {
        val expectedCorpCollection = "Corporation Action: Corporation $corpID is starting to collect garbage."
        assertNextLine(expectedCorpCollection)
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

    private suspend fun RestrictionICantMove.initializationAssertion() {
        assertNextLine("Initialization Info: mapShallowAndDeep.json successfully parsed and validated.")
        assertNextLine("Initialization Info: OneCorpTwoShips.json successfully parsed and validated.")
        assertNextLine("Initialization Info: 1GarbageAndRestriction.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
    }

    private suspend fun RestrictionICantMove.simulationStatsAssertion(corporationId: Int) {
        assertNextLine("Simulation Info: Simulation ended.")
        assertNextLine("Simulation Info: Simulation statistics are calculated.")
        assertNextLine("Simulation Statistics: Corporation $corporationId collected 0 of garbage.")
        assertNextLine("Simulation Statistics: Total amount of plastic collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of oil collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of chemicals collected: 0.")
        assertNextLine("Simulation Statistics: Total amount of garbage still in the ocean: 1000.")
    }
}
