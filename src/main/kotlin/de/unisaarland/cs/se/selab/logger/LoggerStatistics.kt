package de.unisaarland.cs.se.selab.logger

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.tiles.Sea

/** Logger Statistics class*/
object LoggerStatistics {
    val sea = Sea ()
    /** Logged whenever statistics are calculated. */
    fun logSimulationStatisticsCalculated() {
        Logger.log("Simulation Info: Simulation statistics are calculated.")
    }

    /** Log the statistics of the simulation.
     * will have corporations: List<Corporation> */
    fun logSimulationStatistics(corporations: List<Corporation>) {
        logSimulationStatisticsCalculated()
        for (corporation in corporations) {
            if (Logger.map.containsKey(corporation.id)) {
                Logger.map[corporation.id]?.let { statsForCorporation(corporation.id, it) }
            } else {
                statsForCorporation(corporation.id, 0)
            }
        }
        totalPlastic(Logger.totalPlasticCollected)
        totalOil(Logger.totalOilCollected)
        totalChemicals(Logger.totalChemicalsCollected)
        val amountCollected = Logger.totalOilCollected + Logger.totalChemicalsCollected + Logger.totalOilCollected
        totalGarbageInOcean(sea.garbageOnMap - amountCollected)
    }

    /** Statistics for corporation */
    private fun statsForCorporation(corporationId: Int, amount: Int) {
        Logger.log("Simulation Statistics: Corporation $corporationId collected $amount of garbage.")
    }

    /** Statistics for Plastic*/
    private fun totalPlastic(amount: Int) {
        Logger.log("Simulation Statistics: Total amount of plastic collected: $amount.")
    }

    /** Statistics for Oil*/
    private fun totalOil(amount: Int) {
        Logger.log("Simulation Statistics: Total amount of oil collected: $amount.")
    }

    /** Statistics for Chemicals*/
    private fun totalChemicals(amount: Int) {
        Logger.log("Simulation Statistics: Total amount of chemicals collected: $amount.")
    }

    /** Statistics for Garbage*/
    private fun totalGarbageInOcean(amount: Int) {
        Logger.log("Simulation Statistics: Total amount of garbage still in the ocean: $amount.")
    }
}
