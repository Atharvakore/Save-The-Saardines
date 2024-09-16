package de.unisaarland.cs.se.selab.tasks

import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.ships.Ship

class ContainerReward(id: Int, private val capability: CollectingShip, private val container: Container) : Reward(id) {
    override fun applyReward(ship: Ship) {
        if (!ship.capabilities.any { it is CollectingShip }) {
            ship.capabilities.add(capability)
        }
        ship.capabilities.filterIsInstance<CollectingShip>().forEach {
            it.auxiliaryContainers.add(container)
        }
    }
}