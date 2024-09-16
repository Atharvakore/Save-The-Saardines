package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Tile

class CooperateTask(
    override val tick: Int,
    override val id: Int,
    override val taskShip: Ship,
    override val reward: Reward,
    override val rewardShip: Ship,
    override val corporation: Corporation,
): Task(tick, id, taskShip, reward, rewardShip, corporation) {
    private var otherCorporation: Corporation? = null
    private var tile: Tile? = null
    public fun getTile(): Tile? {
        return tile
    }
    public fun getOtherCorporation(): Corporation? {
        return otherCorporation
    }
    public fun setTile(t: Tile) {
        this.tile = t
    }
    public fun setOtherCorporation(corporation: Corporation) {
        this.otherCorporation = corporation
    }
    override fun checkCondition(): Boolean {
        TODO("Not yet implemented")
    }

    override fun actUponTick(currentTick: Int): Boolean {
        TODO("Not yet implemented")
    }
}