package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.Ship

class PirateAttack(
    private val id: Int,
    private val fireTick: Int,
    private val ship: Ship,
    private val owningCorporation: Corporation
) : Event(id, fireTick) {
    override fun toString(): String {
        return "Pirate Attack"
    }
    override fun actUponTick(currentTick: Int): Boolean {
        if (currentTick == fireTick) {
            owningCorporation.ownedShips.remove(ship)
            return true
        }
        return false
    }
}
