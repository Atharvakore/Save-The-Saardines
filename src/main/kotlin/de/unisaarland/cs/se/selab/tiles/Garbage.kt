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
            // WHY IS THIS IF STATEMENT HERE WITH SAME BLOCKS OF CODE ??
            if (target is DeepOcean) {
                currentTile.amountOfGarbageDriftedThisTick += drifted
                Logger.logCurrentDriftGarbage(type, newGarbage.id, drifted, currentTile.id, target.id)
            } else {
                currentTile.amountOfGarbageDriftedThisTick += drifted
                Logger.logCurrentDriftGarbage(type, newGarbage.id, drifted, currentTile.id, target.id)
            }
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
        } else {
            currentTile.garbage.remove(this)
        }

        val garbageSum = targetTile.garbage.filter { it.type == GarbageType.OIL }.sumOf { it.amount }
        /**
         * THIS CHECK SHOULD BE MADE BEFORE, WHAT IF THERE'S NO TILE WHERE OIL CAN BE DRIFTED BUT WE ARE STILL
         * DEDUCTING THE AMOUNT
         */
        // THIS COULD BE FURTHER OPTIMIZED, WILL DO IT LATER IF ENOUGH TIME
        if (garbageSum + drifted > MAXOILCAP) {
            val targetsList = getTilesPath(currentTile, localCurrent)
            val tile = checkOilCap(targetsList, newGarbage.amount)
            if (tile != null) {
                target = tile
                currentTile.amountOfGarbageDriftedThisTick += drifted
                Logger.logCurrentDriftGarbage(type, newGarbage.id, drifted, currentTile.id, target.id)
            }
            // THE CASE WHERE TILE==NULL SHOULD BE HANDLED HERE, OTHERWISE WE ARE USING TARGETTILE
        } else {
            currentTile.amountOfGarbageDriftedThisTick += drifted
            Logger.logCurrentDriftGarbage(type, newGarbage.id, drifted, currentTile.id, target.id)
        }
        return Pair(target, newGarbage)
    }

    private fun checkOilCap(path: List<Tile?>, amount: Int): Tile? {
        val listOfTiles = path.reversed()
        // val targetTile: Tile? = null

        for (tile in listOfTiles) {
            if (tile != null) {
                val garbageSum = tile.garbage.filter { it.type == GarbageType.OIL }.sumOf { it.amount }
                if (garbageSum + amount < MAXOILCAP) {
                    return tile
                }
            }
        }
        return null
    }

    private fun getTilesPath(currentTile: DeepOcean, current: Current): List<Tile?> {
        val distance = current.speed / TEN
        val dir = current.direction
        val tilesPath = mutableListOf<Tile?>()

        if (distance == 0) {
            return listOf(currentTile)
        } else {
            for (i in 0..distance) {
                tilesPath.add(currentTile.getTileInDirection(i, dir))
            }
            return tilesPath.toList()
        }
    }

    /**
     * Drift function for storm. A drift that sweeps away all the garbage
     * */
    fun stormDrift(
        speed: Int,
        direction: Direction,
        currentTile: Tile,
        corporations: List<Corporation>,
        garbageToAdd: MutableMap<Tile, MutableList<Garbage>>,
        garbageToRemove: MutableMap<Tile, MutableList<Garbage>>,
    ) {
        val targetTile = currentTile.getTileInDirection(speed / TEN, direction) ?: return
        var newGarbage = this

        when (this.type) {
            GarbageType.OIL -> {
                val totalOilAmount = targetTile.garbage
                    .filter { it.type == GarbageType.OIL }
                    .sumOf { it.amount }

                if (totalOilAmount < MAXOILCAP) {
                    val driftableAmount = MAXOILCAP - totalOilAmount
                    if (this.amount <= driftableAmount) {
                        newGarbage = createGarbage(amount, GarbageType.OIL)
                        garbageToAdd.getOrPut(targetTile) { mutableListOf() }.add(
                            newGarbage
                        )
                        // targetTile.addGarbage(newGarbage)
                        this.amount = 0
                    } else {
                        newGarbage = createGarbage(driftableAmount, GarbageType.OIL)
                        garbageToAdd.getOrPut(targetTile) { mutableListOf() }.add(
                            newGarbage
                        )
                        // targetTile.addGarbage(newGarbage)
                        this.amount -= driftableAmount
                    }
                }
            }

            GarbageType.PLASTIC -> {
                garbageToAdd.getOrPut(targetTile) { mutableListOf() }.add(
                    createGarbage(this.amount, GarbageType.PLASTIC)
                )
                // targetTile.addGarbage(createGarbage(this.amount, GarbageType.PLASTIC))
                this.amount = 0
            }

            else -> {
                if (targetTile is DeepOcean) {
                    this.amount = 0
                    currentTile.garbage = currentTile.garbage.filter { it.id == this.id }.toMutableList()
                } else {
                    garbageToAdd.getOrPut(targetTile) { mutableListOf() }.add(
                        createGarbage(this.amount, GarbageType.CHEMICALS)
                    )
                    // targetTile.addGarbage(createGarbage(this.amount, GarbageType.CHEMICALS))
                    this.amount = 0
                }
            }
        }

        corporations.forEach { corporation ->
            corporation.partnerGarbage.remove(newGarbage.id)
            corporation.partnerGarbage.putIfAbsent(newGarbage.id, targetTile)
        }

        if (this.amount == 0) {
            garbageToRemove.getOrPut(currentTile) { mutableListOf() }.add(this)
            // currentTile.garbage.remove(this)
        }
    }
}
