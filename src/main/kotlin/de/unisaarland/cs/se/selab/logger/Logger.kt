package de.unisaarland.cs.se.selab.logger

import de.unisaarland.cs.se.selab.tiles.GarbageType
import java.io.PrintWriter

/**
 * The logger class, responsible for logging the simulation events.
 */
object Logger {
    /** The stream that this logger writes to. */
    var totalOilCollected = 0
    var totalPlasticCollected = 0
    var totalChemicalsCollected = 0
    val map: MutableMap<Int, Int> = mutableMapOf()
    var output: LogOutputStream = BackedLogOutputStream(PrintWriter(System.out))

    /** Logging function*/
    fun log(message: String) {
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
}
