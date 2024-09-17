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

    /**
     * drifts big garbage which have more amount than Intensity * 50
     */

    private fun driftBigGarbages (ammounttobedrifted:Int, g: Garbage, source: DeepOcean, target: Tile?){
        if(g.type == GarbageType.OIL){
            if (target!!.currentOilLevel() + g.amount <= THOUSAND) {
               target.addGarbage( createGarbage(ammounttobedrifted, GarbageType.OIL))
                source.garbage [source.garbage.indexOf(g)].amount-=ammounttobedrifted
                return
            }
        }
        target!!.addGarbage( createGarbage(ammounttobedrifted, g.type))
        source.garbage [source.garbage.indexOf(g)].amount-=ammounttobedrifted
        source.amountOfGarbageDriftedThisTick = ammounttobedrifted
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
