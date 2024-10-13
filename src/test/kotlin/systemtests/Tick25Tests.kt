package systemtests

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import test.kotlin.Witchcraft

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Tick25Tests {
    var map = Map()
    val sea = Sea()

    /**
     * Setup for Tick 25 simulation for complete one tick
     */
    @BeforeAll
    fun setup() {
        map.createTestingMap()
        val container = Container(GarbageType.OIL, 5000)
        val capabilityC = CollectingShip(mutableListOf(container))
        val capabilityS = ScoutingShip(5)
        val scoutingShip = Ship(1, 100, 25, 3000, 10, mutableListOf(capabilityS))
        val collectingShip = Ship(2, 100, 25, 3000, 10, mutableListOf(capabilityC))
        scoutingShip.position = sea.getTileById(75)!!
        collectingShip.position = sea.getTileById(23)!!
        val g = Garbage(1, 800, GarbageType.OIL, emptySet())
        sea.getTileById(66)?.addGarbage(g)
        val c1 = Corporation(
            1,
            "c1",
            ownedShips = mutableListOf(scoutingShip, collectingShip),
            mutableListOf(),
            mutableListOf(GarbageType.OIL),
            mutableListOf(),
        )
        Witchcraft.swallowObject(c1)
    }
}
