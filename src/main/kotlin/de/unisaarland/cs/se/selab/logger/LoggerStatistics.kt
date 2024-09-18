package de.unisaarland.cs.se.selab.logger

import de.unisaarland.cs.se.selab.corporation.Corporation
/** Logger Statistics class*/
object LoggerStatistics {
    /** Logged whenever statistics are calculated. */
    fun logSimulationStatisticsCalculated() {
        Logger.log("Simulation Info: Simulation statistics are calculated.")
    }

    /** Log the statistics of the simulation.
     * will have corporations: List<Corporation> */
    fun logSimulationStatistics(corporations: MutableList<Corporation>) {
        logSimulationStatisticsCalculated()
        for (corporation in corporations) {
            if (Logger.map.containsKey(corporation.id)) {
                statsForCorporation(corporation.id, Logger.map[corporation.id]!!)
            } else {
                statsForCorporation(corporation.id, 0)
            }
        }
        totalPlastic(Logger.totalPlasticCollected)
        totalChemicals(Logger.totalChemicalsCollected)
        totalOil(Logger.totalOilCollected)
        totalGarbageInOcean(Logger.totalOilCollected + Logger.totalChemicalsCollected + Logger.totalOilCollected)
    }

    /** Statistics for corporation */
    private fun statsForCorporation(corporationId: Int, amount: Int) {
        Logger.log("Simulation Statistics: Corporation $corporationId collected $amount of garbage.")
    }

    /** Statistics for Plastic*/
    private fun totalPlastic(amount: Int) {
        Logger.log("Simulation Statistics: Total amount of plastic collected: $amount")
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
