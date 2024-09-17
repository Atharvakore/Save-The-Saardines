package de.unisaarland.cs.se.selab.logger

import de.unisaarland.cs.se.selab.events.Event
import de.unisaarland.cs.se.selab.tasks.Reward
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.corporation.Corporation
import java.io.PrintWriter

/**
 * The logger class, responsible for logging the simulation events.
 */
object Logger {
    /** The stream that this logger writes to. */
    private var totalOilCollected = 0
    private var totalPlasticCollected = 0
    private var totalChemicalsCollected = 0
    lateinit var map: MutableMap<Int, Int>
    var output: LogOutputStream = BackedLogOutputStream(PrintWriter(System.out))
    private fun log(message: String) {
        output.write(message)
    }

    /** Log the initialization of the simulation. */
    fun logInitializationInfoSuccess(filename: String) {
        log(" Initialization Info: $filename successfully parsed and validated.")
    }

    /** Log the failure of the initialization of the simulation. */
    fun logInitializationInfoFail(filename: String) {
        log("Initialization Info: $filename is invalid.")
    }

    /** Log the start of the simulation. */
    fun logSimulationStarted() {
        log("Simulation Info: Simulation started.")
    }

    /** Log the end of the simulation. */
    fun logSimulationEnded() {
        log("Simulation Info: Simulation ended.")
    }

    /** Log the start of a simulation tick. */
    fun logSimulationTick(tick: Int) {
        log("Simulation Info: Tick $tick started.")
    }

    /** Log a call of Corporation.moveShips(). */
    fun logCorporationStartMoveShips(corporationId: Int) {
        log("Corporation Action: Corporation $corporationId is starting to move its ships.")
    }

    /** Log a call of Ship.move(). */
    fun logShipMovement(shipId: Int, speed: Int, tileId: Int) {
        log("Ship Movement: Ship $shipId moved with speed $speed to tile $tileId.")
    }

    /** Logged whenever a ship attaches a tracker to a garbage pile. */
    fun logCorporationAttachedTracker(corporationId: Int, garbageId: Int, shipId: Int) {
        log("Corporation Action: Corporation $corporationId attached tracker to garbage $garbageId with ship $shipId.")
    }

    /** Logged whenever Corporation.collectGarbage() is called. */
    fun logCorporationStartCollectGarbage(corporateId: Int) {
        log("Corporation Action: Corporation $corporateId is starting to collect garbage.")
    }

    /** Logged to signify that a ship has collected garbage. */
    fun logGarbageCollectionByShip(shipId: Int, garbageType: GarbageType, garbageId: Int, amount: Int) {
        fun logGarbageCollectionByShip(ship: Ship, garbageType: GarbageType, garbageId: Int, amount: Int) {
            val shipId: Int = ship.id
            val corporationId: Int = ship.getOwnerCorporation().id
            when (garbageType) {
                GarbageType.OIL -> totalOilCollected += amount
                GarbageType.PLASTIC -> totalPlasticCollected += amount
                GarbageType.CHEMICALS -> totalChemicalsCollected += amount
            }
            map[corporationId] = map.getOrDefault(corporationId, 0) + amount
            log("Garbage Collection: Ship $shipId collected $amount of garbage $garbageType with $garbageId.")
        }
    }
        /** Log the start of cooperation between corporations. */
    fun logCorporationCooperationStart(corporationId: Int) {
        log("Corporation Action: Corporation $corporationId is starting to cooperate with other corporations.")
    }

    /** Log the cooperation between corporations. */
    fun logCooperationBetweenCorporations(
        corporationId: Int,
        otherCorporationId: Int,
        shipId: Int,
        cooperatedShipId: Int
    ) {
        log(
            "Cooperation: Corporation $corporationId" +
                    " cooperated with corporation" +
                    " $otherCorporationId with ship" +
                    " $shipId to ship $cooperatedShipId."
        )
    }

    /** Logged whenever refueling is started. */
    fun logCorporationRefueling(corporationId: Int) {
        log("Corporation Action: Corporation $corporationId is starting to refuel.")
    }

    /** Logged whenever a ship is refueled. */
    fun logRefuelingShip(shipId: Int, tileId: Int) {
        log("Refueling: Ship $shipId refueled at harbor $tileId.")
    }

    /** Logged whenever a ship is unloaded. */
    fun logUnloadShip(shipId: Int, amount: Int, garbageType: GarbageType, tileId: Int) {
        log("Unload: Ship $shipId unloaded $amount of garbage $garbageType at harbor $tileId.")
    }

    /** Logged whenever a corporation finishes its actions. */
    fun logCorporationFinishedActions(corporationId: Int) {
        log("Corporation Action: Corporation $corporationId finished its actions.")
    }

    /** Log drifting of garbage. */
    fun logCurrentDriftGarbage(
        garbageType: GarbageType,
        garbageId: Int,
        amount: Int,
        startTileId: Int,
        endTileId: Int
    ) {
        log(
            "Current Drift: $garbageType" +
                    " $garbageId with amount" +
                    " $amount drifted from tile" +
                    " $startTileId to tile $endTileId."
        )
    }

    /** Log drifting of ships. */
    fun logCurrentDriftShip(shipId: Int, startTileId: Int, endTileId: Int) {
        log("Current Drift: Ship $shipId drifted from tile $startTileId to tile $endTileId.")
    }

    /** Log the start of an event. */
    fun logEventStart(eventId: Int, eventType: Event) {
        log("Event: Event $eventId of type $eventType happened.")
    }

    /** Log a task being added to a ship. */
    fun logTaskAddedToShip(taskId: Int, taskType: Task, shipId: Int, tileId: Int) {
        log("Task: Task $taskId of type $taskType with ship $shipId is added with destination $tileId.")
    }

    /** Log a reward being received. */
    fun logRewardReceived(taskId: Int, shipId: Int, rewardType: Reward) {
        log("Reward: Task $taskId: Ship $shipId received reward of type $rewardType.")
    }

    /** Logged whenever statistics are calculated. */
    fun logSimulationStatisticsCalculated() {
        log("Simulation Info: Simulation statistics are calculated.")
    }

    /** Log the statistics of the simulation.
     * will have corporations: List<Corporation> */
    fun logSimulationStatistics(corporations: MutableList<Corporation>) {
        logSimulationStatisticsCalculated()
        for (corporation in corporations) {
            if (map.containsKey(corporation.id)) {
                statsForCorporation(corporation.id, map[corporation.id]!!)
            } else {
                statsForCorporation(corporation.id, 0)
            }
        }
        totalPlastic(totalPlasticCollected)
        totalChemicals(totalChemicalsCollected)
        totalOil(totalOilCollected)
        totalGarbageInOcean(totalOilCollected + totalChemicalsCollected + totalOilCollected)
    }

    /** Statistics for corporation */
    private fun statsForCorporation(corporationId: Int, amount: Int) {
        log("Simulation Statistics: Corporation $corporationId collected $amount of garbage.")
    }

    /** Statistics for Plastic*/
    private fun totalPlastic(amount: Int) {
        log("Simulation Statistics: Total amount of plastic collected: $amount")
    }

    /** Statistics for Oil*/
    private fun totalOil(amount: Int) {
        log("Simulation Statistics: Total amount of oil collected: $amount.")
    }

    /** Statistics for Chemicals*/
    private fun totalChemicals(amount: Int) {
        log("Simulation Statistics: Total amount of chemicals collected: $amount.")
    }

    /** Statistics for Garbage*/
    private fun totalGarbageInOcean(amount: Int) {
        log("Simulation Statistics: Total amount of garbage still in the ocean: $amount.")
    }
}
