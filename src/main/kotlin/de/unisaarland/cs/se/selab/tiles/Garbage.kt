package de.unisaarland.cs.se.selab.tiles

import de.unisaarland.cs.se.selab.corporation.Corporation


const val FIFTY = 50
const val TEN = 10
const val THOUSAND = 1000

/**
 * Garbage class implementing all minor stuff related to Garbage
 */
class Garbage(
    val id: Int,
    var amount: Int,
    val type: GarbageType,
    private var trackedBy: Set<Corporation>?,
) {
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
     */
    public fun drift(currentTile: DeepOcean) {
        val localcurrent: Current? = currentTile.getCurrent()
        var ammounttobedrifted = localcurrent?.intensity?.times(FIFTY)
        var toBedrifted = localcurrent?.speed?.div(TEN)
            ?.let { localcurrent.direction?.let { it1 -> currentTile.getTileInDirection(it, it1) } }
        for (g in currentTile.garbage.sortedBy { it.id }) {
            if(g.amount + currentTile.amountOfGarbageDriftedThisTick < ammounttobedrifted!!) {
                if (g.type == GarbageType.OIL) {
                    driftOil(g, currentTile, toBedrifted)
                    ammounttobedrifted = ammounttobedrifted?.minus(g.amount)
                }
                driftPlasticandChemicals(g, currentTile, toBedrifted)
                ammounttobedrifted = ammounttobedrifted?.minus(g.amount)
            }
        }


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
    }

    /**
     * drifts plastic and chemicals
     */
    private fun driftPlasticandChemicals(g: Garbage, source: DeepOcean, target: Tile?){
        if (target != null) {
            if (target.currentOilLevel() + g.amount <= THOUSAND) {
                target.addGarbage(g)
                source.garbage.filter { it == g }
                source.amountOfGarbageDriftedThisTick += g.amount
            }
        }
    }

}
