package events

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.events.PirateAttack
import de.unisaarland.cs.se.selab.ships.Ship
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PirateAttackTest {
    private var corp: Corporation =
        Corporation(1, "HiHA", mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())
    var ship1 = Ship(1, 50, 10, 1000, 5, mutableListOf())
    var ship2 = Ship(2, 50, 10, 1000, 5, mutableListOf())
    var ship3 = Ship(3, 50, 10, 1000, 5, mutableListOf())
    private var eventPirateAttack = PirateAttack(1, 5, ship1, corp)

    @Test
    fun testBasicCurrentTickIsFireTick() {
        assert(eventPirateAttack.actUponTick(5))
        assert(!corp.ownedShips.contains(ship1))
    }
}
