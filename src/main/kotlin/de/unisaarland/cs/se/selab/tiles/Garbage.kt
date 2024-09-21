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
    public fun drift(tile: Tile) {
        if (tile is DeepOcean) {
            driftHelper(tile)
        }
        return
    }

    /**
     * helps drifting
     */
    public fun driftHelper(currentTile: DeepOcean) {
        val localCurrent: Current? = currentTile.getCurrent()

        var amountToBeDrifted = (localCurrent?.intensity ?: return) * FIFTY
        var targetForDriftingTile = currentTile.getTileInDirection(localCurrent.speed / TEN, localCurrent.direction)
        if (checkTargetTile(currentTile) && currentTile.garbage.isNotEmpty()) {
            for (g in currentTile.garbage.sortedBy { it.id }) {
                if (g.type == GarbageType.OIL) {
                    amountToBeDrifted = handleOilGarbage(
                        g,
                        targetForDriftingTile!!,
                        currentTile,
                        amountToBeDrifted
                    )
                }
                amountToBeDrifted = driftGarbage(
                    g,
                    currentTile,
                    targetForDriftingTile,
                    amountToBeDrifted
                )
            }
        }
    }

    private fun handleOilGarbage(
        g: Garbage,
        targetForDriftingTile: Tile,
        currentTile: DeepOcean,
        amountToBeDrifted: Int
    ): Int {
        if (targetForDriftingTile.currentOilLevel() + g.amount <= THOUSAND) {
            currentTile.garbage.minus(g)
            targetForDriftingTile.garbage.plus(g)
            return amountToBeDrifted - g.amount
        } else {
            createGarbage(THOUSAND - currentTile.currentOilLevel(), GarbageType.OIL)
            val index = currentTile.garbage.indexOf(g)
            currentTile.garbage[index].amount -= THOUSAND - currentTile.currentOilLevel()
            targetForDriftingTile.garbage.plus(createGarbage(THOUSAND - currentTile.currentOilLevel(), GarbageType.OIL))
            return amountToBeDrifted - (THOUSAND - currentTile.currentOilLevel())
        }
    }

    private fun driftGarbage(
        g: Garbage,
        currentTile: DeepOcean,
        targetForDriftingTile: Tile?,
        amountToBeDrifted: Int
    ): Int {
        var remainingAmount = amountToBeDrifted

        if (remainingAmount <= g.amount && remainingAmount > 0) {
            if (remainingAmount == g.amount) {
                targetForDriftingTile?.garbage?.plus(g)
                currentTile.garbage.minusElement(g)
                remainingAmount = 0
            } else {
                currentTile.garbage[0].amount -= remainingAmount
                targetForDriftingTile?.garbage?.plus(createGarbage(remainingAmount, g.type))
                remainingAmount = 0
            }
        } else {
            currentTile.garbage.minusElement(g)
            targetForDriftingTile?.garbage?.plus(g)
            remainingAmount -= g.amount
        }

        return remainingAmount
    }

    /**
     * checks if given tile is Valid or not
     */
    private fun checkTargetTile(tile: Tile?): Boolean {
        if (tile is Shore || tile == null) {
            return false
        }
        return true
    }
}
