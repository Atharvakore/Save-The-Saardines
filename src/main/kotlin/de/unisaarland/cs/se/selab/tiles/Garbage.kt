package de.unisaarland.cs.se.selab.tiles

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.logger.Logger

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
     * drifts garbage
     */
    fun drift(tile: DeepOcean, targetTile: Tile, current: Current): Garbage? {
        return driftHelper(tile, targetTile, current)
    }

    /**
     * helps drifting
     */
    private fun driftHelper(currentTile: DeepOcean, targetTile: Tile, localCurrent: Current): Garbage? {
        val result: Garbage?
        val toBeDrifted: Int
        val driftAmount = localCurrent.intensity * DRIFTAMOUNTPERPOINTINTENSITY
        if (currentTile.amountOfGarbageDriftedThisTick < driftAmount) {
            toBeDrifted = driftAmount - currentTile.amountOfGarbageDriftedThisTick
        } else {
            return null
        }
        when (this.type) {
            GarbageType.OIL -> {
                result = handleOilGarbage(currentTile, targetTile, toBeDrifted)
            }
            GarbageType.CHEMICALS -> {
                result = handleChemicalsGarbage(currentTile, targetTile, toBeDrifted)
            }
            else -> {
                val drifted = minOf(this.amount, toBeDrifted)
                this.amount -= drifted
                result = createGarbage(drifted, GarbageType.PLASTIC)
                currentTile.amountOfGarbageDriftedThisTick += drifted
                Logger.logCurrentDriftGarbage(type, this.id, drifted, currentTile.id, targetTile.id)
            }
        }
        return result
    }

    private fun handleChemicalsGarbage(currentTile: DeepOcean, targetTile: Tile, toBeDrifted: Int): Garbage? {
        val drifted = minOf(this.amount, toBeDrifted)
        this.amount -= drifted
        if (targetTile is DeepOcean) {
            currentTile.amountOfGarbageDriftedThisTick += drifted
            Logger.logCurrentDriftGarbage(type, this.id, drifted, currentTile.id, targetTile.id)
        } else {
            val newGarbage = createGarbage(drifted, GarbageType.CHEMICALS)
            currentTile.amountOfGarbageDriftedThisTick += drifted
            Logger.logCurrentDriftGarbage(type, this.id, drifted, currentTile.id, targetTile.id)
            return newGarbage
        }
        return null
    }
    private fun handleOilGarbage(
        currentTile: DeepOcean,
        targetTile: Tile,
        toBeDrifted: Int
    ): Garbage? {
        val result: Garbage?
        val maxDrift = MAXOILCAP - targetTile.getAmountOfType(GarbageType.OIL)
        var drifted = minOf(this.amount, toBeDrifted, maxDrift)
        if (drifted > 0) {
            this.amount -= drifted
            currentTile.amountOfGarbageDriftedThisTick += drifted
            Logger.logCurrentDriftGarbage(type, id, drifted, currentTile.id, targetTile.id)
            return createGarbage(drifted, GarbageType.OIL)
        } else {
            drifted = minOf(this.amount, toBeDrifted)
            currentTile.amountOfGarbageDriftedThisTick += drifted
            if (drifted > 0) {
                Logger.logCurrentDriftGarbage(type, id, drifted, currentTile.id, currentTile.id)
                result = createGarbage(drifted, GarbageType.OIL)
            } else {
                result = null
            }
        }
        return result
    }

    /**
     * Drift function for storm. A drift that sweeps away all the garbage
     * */
    fun stormDrift(speed: Int, direction: Direction, currentTile: Tile) {
        val targetTile = currentTile.getTileInDirection(speed / TEN, direction) ?: return

        when (this.type) {
            GarbageType.OIL -> {
                val totalOilAmount = targetTile.garbage
                    .filter { it.type == GarbageType.OIL }
                    .sumOf { it.amount }

                if (totalOilAmount < MAXOILCAP) {
                    val driftableAmount = MAXOILCAP - totalOilAmount
                    if (this.amount <= driftableAmount) {
                        targetTile.addGarbage(createGarbage(amount, GarbageType.OIL))
                        this.amount = 0
                    } else {
                        targetTile.addGarbage(createGarbage(driftableAmount, GarbageType.OIL))
                        this.amount -= driftableAmount
                    }
                }
            }

            GarbageType.PLASTIC -> {
                targetTile.addGarbage(createGarbage(this.amount, GarbageType.PLASTIC))
                this.amount = 0
            }

            else -> {
                if (targetTile is DeepOcean) {
                    this.amount = 0
                } else {
                    targetTile.addGarbage(createGarbage(this.amount, GarbageType.CHEMICALS))
                    this.amount = 0
                }
            }
        }
    }
}
