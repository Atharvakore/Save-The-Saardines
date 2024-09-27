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
    fun drift(currentTile: DeepOcean, targetTile: Tile, localCurrent: Current): Pair<Tile, Garbage>? {
        val result: Pair<Tile, Garbage>?
        val toBeDrifted: Int
        val driftAmount = localCurrent.intensity * DRIFTAMOUNTPERPOINTINTENSITY
        if (currentTile.amountOfGarbageDriftedThisTick < driftAmount) {
            toBeDrifted = driftAmount - currentTile.amountOfGarbageDriftedThisTick
        } else {
            return null
        }

        result = when (this.type) {
            GarbageType.OIL -> {
                handleOilGarbage(currentTile, targetTile, toBeDrifted, localCurrent)
            }
            else -> {
                handlePlasticGarbage(currentTile, targetTile, toBeDrifted)
            }
        }
        return result
    }

    private fun handlePlasticGarbage(currentTile: Tile, targetTile: Tile, toBeDrifted: Int): Pair<Tile, Garbage>? {
        var newGarbage = this
        val drifted = minOf(this.amount, toBeDrifted)
        this.amount -= drifted
        if (this.amount > 0) {
            newGarbage = createGarbage(drifted, GarbageType.PLASTIC)
            currentTile.amountOfGarbageDriftedThisTick += drifted
            Logger.logCurrentDriftGarbage(type, newGarbage.id, drifted, currentTile.id, targetTile.id)
            return Pair(targetTile, newGarbage)
        } else {
            newGarbage.amount = drifted
            currentTile.amountOfGarbageDriftedThisTick += drifted
            Logger.logCurrentDriftGarbage(type, newGarbage.id, newGarbage.amount, currentTile.id, targetTile.id)
            currentTile.garbage.remove(this)
            return Pair(targetTile, newGarbage)
        }
    }

    private fun handleOilGarbage(
        currentTile: DeepOcean,
        targetTile: Tile,
        toBeDrifted: Int,
        localCurrent: Current
    ): Pair<Tile, Garbage>? {
        var target = targetTile
        val drifted = minOf(this.amount, toBeDrifted)
        this.amount -= drifted
        var newGarbage: Garbage = this

        if (this.amount > 0) {
            newGarbage = createGarbage(drifted, GarbageType.OIL)
        } else {
            newGarbage.amount = drifted
            currentTile.garbage.remove(newGarbage)
        }

        val garbageSum = targetTile.garbage.filter { it.type == GarbageType.OIL }.sumOf { it.amount }
        /**
         * THIS CHECK SHOULD BE MADE BEFORE, WHAT IF THERE'S NO TILE WHERE OIL CAN BE DRIFTED BUT WE ARE STILL
         * DEDUCTING THE AMOUNT
         */
        // THIS COULD BE FURTHER OPTIMIZED, WILL DO IT LATER IF ENOUGH TIME
        if (garbageSum + drifted > MAXOILCAP) {
            val distance = localCurrent.speed / TEN
            val targetsList = getTilesPath(currentTile, distance, localCurrent.direction)
            val tile = checkOilCap(targetsList, newGarbage.amount)
            if (tile != null) {
                target = tile
                currentTile.amountOfGarbageDriftedThisTick += drifted
                Logger.logCurrentDriftGarbage(type, newGarbage.id, drifted, currentTile.id, target.id)
                return Pair(target, newGarbage)
            } else {
                return null
            }
            // THE CASE WHERE TILE==NULL SHOULD BE HANDLED HERE, OTHERWISE WE ARE USING TARGETTILE
        } else {
            currentTile.amountOfGarbageDriftedThisTick += drifted
            Logger.logCurrentDriftGarbage(type, newGarbage.id, drifted, currentTile.id, target.id)
            return Pair(target, newGarbage)
        }
    }

    private fun checkOilCap(path: List<Tile?>, amount: Int): Tile? {
        val listOfTiles = path.reversed()
        // val targetTile: Tile? = null

        for (tile in listOfTiles) {
            if (tile != null) {
                val garbageSum = tile.garbage.filter { it.type == GarbageType.OIL }.sumOf { it.amount }
                if (garbageSum + amount <= MAXOILCAP) {
                    return tile
                }
            }
        }
        return null
    }

    private fun getTilesPath(currentTile: Tile, distance: Int, dir: Direction): List<Tile?> {
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
        val targetTile = getValidTile(currentTile, speed / TEN, direction) ?: return

        when (this.type) {
            GarbageType.OIL -> {
                val totalOilAmount = targetTile.garbage
                    .filter { it.type == GarbageType.OIL }
                    .sumOf { it.amount }

                if (totalOilAmount + this.amount <= MAXOILCAP) {
                    garbageToAdd.getOrPut(targetTile) { mutableListOf() }.add(this)
                    corporations.forEach { corporation ->
                        corporation.partnerGarbage.remove(this.id)
                        corporation.partnerGarbage.putIfAbsent(this.id, targetTile)
                    }
                } else {
                    val distance = speed / TEN
                    val tilesInPath = getTilesPath(targetTile, distance, direction)
                    val newTarget = checkOilCap(tilesInPath, this.amount) ?: return
                    garbageToAdd.getOrPut(newTarget) { mutableListOf() }.add(this)
                    garbageToRemove.getOrPut(currentTile) { mutableListOf() }.add(this)
                    corporations.forEach { corporation ->
                        corporation.partnerGarbage.remove(this.id)
                        corporation.partnerGarbage.putIfAbsent(this.id, newTarget)
                    }
                }
            }
            GarbageType.PLASTIC -> {
                garbageToAdd.getOrPut(targetTile) { mutableListOf() }.add(this)
                garbageToRemove.getOrPut(currentTile) { mutableListOf() }.add(this)
                corporations.forEach { corporation ->
                    corporation.partnerGarbage.remove(this.id)
                    corporation.partnerGarbage.putIfAbsent(this.id, targetTile)
                }
            }
            else -> {
                if (targetTile is DeepOcean) {
                    garbageToRemove.getOrPut(currentTile) { mutableListOf() }.add(this)
                    corporations.forEach { corporation ->
                        corporation.partnerGarbage.remove(this.id)
                    }
                } else {
                    garbageToAdd.getOrPut(targetTile) { mutableListOf() }.add(this)
                    garbageToRemove.getOrPut(currentTile) { mutableListOf() }.add(this)
                    corporations.forEach { corporation ->
                        corporation.partnerGarbage.remove(this.id)
                        corporation.partnerGarbage.putIfAbsent(this.id, targetTile)
                    }
                }
            }
        }
    }

    private fun getValidTile(currentTile: Tile, distance: Int, direction: Direction): Tile? {
        var targetTile = currentTile.getTileInDirection(distance, direction)

        if (distance == 0) {
            targetTile = currentTile
        } else {
            for (i in 1..distance) {
                currentTile.getTileInDirection(i, direction)
                    ?: return currentTile.getTileInDirection(i - 1, direction)
            }
        }
        return targetTile
    }
}
