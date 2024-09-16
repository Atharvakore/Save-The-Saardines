package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Tile

class ExploreMapTask(
    override val tick: Int,
    override val id: Int,
    override val taskShip: Ship,
    override val reward: Reward,
    override val rewardShip: Ship,
    override val corporation: Corporation,
): Task(tick, id, taskShip, reward, rewardShip, corporation) {
    private var tile: Tile? = null
    fun getTile(): Tile? {
        return tile
    }
    fun setTile(t: Tile) {
        this.tile = t
    }
    override fun checkCondition(): Boolean {
        TODO("Not yet implemented")
    }

    override fun actUponTick(currentTick: Int): Boolean {
        TODO("Not yet implemented")
    }
}