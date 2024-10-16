package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.logger.LoggerEventsAndTasks
import de.unisaarland.cs.se.selab.ships.Ship

/** The pirate attack event. */
class PirateAttack(
    override val id: Int,
    override val fireTick: Int,
    private val ship: Ship,
    private val owningCorporation: Corporation
) : Event(id, fireTick) {
    override fun toString(): String {
        return "PIRATE_ATTACK"
    }
    override fun actUponTick(currentTick: Int, corporations: List<Corporation>): Boolean {
        if (currentTick == fireTick) {
            owningCorporation.ownedShips.remove(ship)
            LoggerEventsAndTasks.logEventStart(id, this)
            return true
        }
        return false
    }
}
