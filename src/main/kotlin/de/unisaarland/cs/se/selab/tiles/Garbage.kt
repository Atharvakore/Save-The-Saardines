package de.unisaarland.cs.se.selab.tiles

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.logger.Logger
import de.unisaarland.cs.se.selab.tiles.MaxGarbageId.createGarbage

/**
 * Garbage class implementing all minor stuff related to Garbage
 */
class Garbage(
    val id: Int,
    var amount: Int,
    val type: GarbageType,
    var trackedBy: Set<Corporation>,
) {

    /**
     * drifts garbage
     */
    fun drift(tile: DeepOcean, targetTile: Tile, current: Current): Pair<Tile, Garbage> {
        return if (tile.id != targetTile.id && targetTile !is Land) {
            driftHelper(tile, targetTile, current)
        } else {
            Pair(tile, this)
        }
    }

    /**
     * helps drifting
     */
    private fun driftHelper(currentTile: DeepOcean, targetTile: Tile, localCurrent: Current): Pair<Tile, Garbage> {
        val result: Pair<Tile, Garbage>
        val toBeDrifted: Int
        val driftAmount = localCurrent.intensity * DRIFTAMOUNTPERPOINTINTENSITY
        if (currentTile.amountOfGarbageDriftedThisTick < driftAmount) {
            toBeDrifted = driftAmount - currentTile.amountOfGarbageDriftedThisTick
        } else {
            return Pair(currentTile, this)
        }

        result = when (this.type) {
            GarbageType.OIL -> {
                handleOilGarbage(currentTile, targetTile, toBeDrifted, localCurrent)
            }

            GarbageType.CHEMICALS -> {
                handleChemicalsGarbage(currentTile, targetTile, toBeDrifted)
            }

            else -> {
                handlePlasticGarbage(currentTile, targetTile, toBeDrifted)
            }
        }
        return result
    }

    private fun handlePlasticGarbage(currentTile: Tile, targetTile: Tile, toBeDrifted: Int): Pair<Tile, Garbage> {
        var newGarbage = this
        val drifted = minOf(this.amount, toBeDrifted)
        this.amount -= drifted
        if (this.amount > 0) {
            newGarbage = createGarbage(drifted, GarbageType.PLASTIC)
        }
        currentTile.amountOfGarbageDriftedThisTick += drifted
        Logger.logCurrentDriftGarbage(type, newGarbage.id, drifted, currentTile.id, targetTile.id)

        return Pair(targetTile, newGarbage)
    }

    private fun handleChemicalsGarbage(currentTile: DeepOcean, target: Tile, toBeDrifted: Int): Pair<Tile, Garbage> {
        val drifted = minOf(this.amount, toBeDrifted)
        this.amount -= drifted
        var newGarbage: Garbage = this
        if (this.amount > 0) {
            newGarbage = createGarbage(drifted, GarbageType.CHEMICALS)
        }
        if (target is DeepOcean) {
            currentTile.amountOfGarbageDriftedThisTick += drifted
            Logger.logCurrentDriftGarbage(type, newGarbage.id, drifted, currentTile.id, target.id)
        } else {
            currentTile.amountOfGarbageDriftedThisTick += drifted
            Logger.logCurrentDriftGarbage(type, newGarbage.id, drifted, currentTile.id, target.id)
        }
        return Pair(target, newGarbage)
    }
    private fun handleOilGarbage(
        currentTile: DeepOcean,
        targetTile: Tile,
        toBeDrifted: Int,
        localCurrent: Current
    ): Pair<Tile, Garbage> {
        var target = targetTile
        val drifted = minOf(this.amount, toBeDrifted)
        this.amount -= drifted
        var newGarbage: Garbage = this
        if (this.amount > 0) {
            newGarbage = createGarbage(drifted, GarbageType.OIL)
        }

        val garbageSum = targetTile.garbage.filter { it.type == GarbageType.OIL }.sumOf { it.amount }

        if (garbageSum + drifted > MAXOILCAP) {
            /**
             * THIS CHECK SHOULD BE MADE BEFORE, WHAT IF THERE'S NO TILE WHERE OIL CAN BE DRIFTED BUT WE ARE STILL
             * DEDUCTING THE AMOUNT
             */
            target = getValidTileInPath(currentTile, targetTile, drifted, localCurrent)
            currentTile.amountOfGarbageDriftedThisTick += drifted
            Logger.logCurrentDriftGarbage(type, newGarbage.id, drifted, currentTile.id, target.id)
        } else {
            currentTile.amountOfGarbageDriftedThisTick += drifted
            Logger.logCurrentDriftGarbage(type, newGarbage.id, drifted, currentTile.id, target.id)
        }
        return Pair(target, newGarbage)
    }

    private fun getValidTileInPath(currentTile: DeepOcean, targetTile: Tile, drifted: Int, current: Current): Tile {
        var distance = current.speed / TEN
        val dir = current.direction
        val tile = currentTile.getTileInDirection(distance, dir)

        if (tile != targetTile) {
            for (i in 0..distance) {
                val temp = currentTile.getTileInDirection(i, dir)
                if (temp == targetTile) {
                    distance = i
                    break
                }
            }
        }

        for (i in distance downTo 0) {
            val temp = currentTile.getTileInDirection(i, dir)
            if (temp != null) {
                val oil = temp.garbage.filter { it.type == GarbageType.OIL }.sumOf { it.amount }
                /**
                 * WHY ARE WE COMPARING THE AMOUNT OF OIL IN THE TILE WITH THE AMOUNT OF DRIFT WITHOUT TAKING THE MAXCAP
                 * FOR WHICH THIS METHOD WAS INITIALLY CALLED INTO CONSIDERATION ??
                 */
                if (oil + drifted <= MAXOILCAP) {
                    return temp
                }
            }
        }
        return targetTile
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
                    currentTile.garbage = currentTile.garbage.filter { it.id == this.id }
                } else {
                    targetTile.addGarbage(createGarbage(this.amount, GarbageType.CHEMICALS))
                    this.amount = 0
                }
            }
        }

        if (this.amount == 0) {
            currentTile.garbage = currentTile.garbage.filter { it != this }
        }
    }
}
