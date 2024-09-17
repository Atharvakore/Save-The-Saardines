package de.unisaarland.cs.se.selab.tiles

import de.unisaarland.cs.se.selab.corporation.Corporation

/**
 * Garbage class implementing all minor stuff related to Garbage
 */
class Garbage(
    val id: Int,
    var amount: Int,
    val type: GarbageType,
    var trackedBy: Set<Corporation>?,
) {
    /** Static methods of garbage (factory method) */
    companion object {
        var maxId: Int = 0

        private fun getNextId(): Int = maxId++

        /**
         * Creates garbage when needed
         */
        fun createGarbage(
            amount: Int,
            type: GarbageType,
        ): Garbage = Garbage(getNextId(), amount, type, null)
    }

    /**
     * drifts possible garbage to the respected tile
     *//* public fun drift(currentTile: DeepOcean) {
         val localcurrent: Current? = currentTile.getCurrent()
         var ammounttobedrifted = localcurrent?.intensity?.times(FIFTY)
         var toBedrifted = localcurrent?.speed?.div(TEN)
             ?.let { localcurrent.direction?.let { it1 -> currentTile.getTileInDirection(it, it1) } }
         for (g in currentTile.garbage.sortedBy { it.id }) {
             if(g.amount >= ammounttobedrifted!!){
                 driftBigGarbages(ammounttobedrifted,g,currentTile,toBedrifted)
             }
             if (g.amount + currentTile.amountOfGarbageDriftedThisTick < ammounttobedrifted!!) {
                 if (g.type == GarbageType.OIL) {
                     driftOil(g, currentTile, toBedrifted)
                     ammounttobedrifted = ammounttobedrifted?.minus(g.amount)
                 }
                 driftPlasticandChemicals(g, currentTile, toBedrifted)
                 ammounttobedrifted = ammounttobedrifted?.minus(g.amount)
             }
         }


     }
    public fun drift(currentTile: DeepOcean) {
        val localCurrent: Current? = currentTile.getCurrent()
        if (localCurrent != null) {
            var amountToBeDrifted = localCurrent.intensity * FIFTY
            var targetForDriftingTile = currentTile.getTileInDirection(localCurrent.speed / TEN, localCurrent.direction)
            for (g in currentTile.garbage.sortedBy { it.id }) {
                if (g.amount >= amountToBeDrifted) {
                    driftBigGarbages(amountToBeDrifted, g, currentTile, targetForDriftingTile)
                }
                if (g.amount + currentTile.amountOfGarbageDriftedThisTick < amountToBeDrifted) {
                    if (g.type == GarbageType.OIL) {
                        driftOil(g, currentTile, targetForDriftingTile)
                        amountToBeDrifted -= g.amount
                    }
                    driftPlasticandChemicals(g, currentTile, targetForDriftingTile)
                    amountToBeDrifted -= g.amount
                }
            }
        }

    }*/
    public fun drift(currentTile: DeepOcean) {
        val localCurrent: Current? = currentTile.getCurrent()
        if (localCurrent != null) {
            var amountToBeDrifted = localCurrent.intensity * FIFTY
            var targetForDriftingTile = currentTile.getTileInDirection(localCurrent.speed / TEN, localCurrent.direction)
            for (g in currentTile.garbage.sortedBy { it.id }) {
                if (g.amount >= amountToBeDrifted) {
                    driftBigGarbages(amountToBeDrifted, g, currentTile, targetForDriftingTile)
                }
                amountToBeDrifted = driftSmallGarbages(amountToBeDrifted, g, currentTile, targetForDriftingTile)
            }
        }
    }
    private fun driftSmallGarbages(amountToBeDrifted: Int, g: Garbage, source: DeepOcean, targetTile: Tile?): Int {
        var amountToBeDriftedTemp = amountToBeDrifted
        if (g.amount + source.amountOfGarbageDriftedThisTick < amountToBeDrifted) {
            if (g.type == GarbageType.OIL) {
                driftOil(g, source, targetTile)
                amountToBeDriftedTemp -= g.amount
            }
            driftPlasticandChemicals(g, source, targetTile)
            amountToBeDriftedTemp -= g.amount
        }
        return amountToBeDriftedTemp
    }

    /**
     * drifts big garbage which have more amount than Intensity * 50
     */

    private fun driftBigGarbages(amountToBeDrifted: Int, g: Garbage, source: DeepOcean, target: Tile?) {
        if (g.type == GarbageType.OIL) {
            if (target!!.currentOilLevel() + g.amount <= THOUSAND) {
                target.addGarbage(createGarbage(amountToBeDrifted, GarbageType.OIL))
                source.garbage[source.garbage.indexOf(g)].amount -= amountToBeDrifted
                return
            }
        }
        target!!.addGarbage(createGarbage(amountToBeDrifted, g.type))
        source.garbage[source.garbage.indexOf(g)].amount -= amountToBeDrifted
        source.amountOfGarbageDriftedThisTick = amountToBeDrifted
        return
    }

    /**
     * drifts oil
     */
    private fun driftOil(g: Garbage, source: DeepOcean, target: Tile?) {
        if (target != null) {
            if (target.currentOilLevel() + g.amount <= THOUSAND) {
                target.addGarbage(g)
                source.garbage.filter { it == g }
                source.amountOfGarbageDriftedThisTick += g.amount
            }
        }
        return
    }

    /**
     * drifts plastic and chemicals
     */
    private fun driftPlasticandChemicals(g: Garbage, source: DeepOcean, target: Tile?) {
        if (target != null) {
            target.addGarbage(g)
            source.garbage.filter { it == g }
            source.amountOfGarbageDriftedThisTick += g.amount
        }
    }
}
