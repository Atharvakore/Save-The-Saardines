package corporation

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.*

class CorporationMoveShipsTest {

    private val factory = UTFactory()

    @BeforeEach
    fun setUp() {
        Sea.tiles.clear()
        Sea.tileIndex = emptyMap()
    }

    @Test
    fun testMoveOneScoutingShipSimple() {
        // test setup
        factory.createTestingMap()
        val initialTile = Sea.getTileByPos(Vec2D(5, 7)) ?: error("Tile not found at position (5,7)")
        val harborTile = Sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), emptyList<Task>())
        val capability = ScoutingShip(5)
        val scoutingShip = Ship(1, 100,25, 3000, 10, mutableListOf(capability))
        scoutingShip.owner = c1
        scoutingShip.position = initialTile
        scoutingShip.name = "unknown"
        c1.ownedShips.add(scoutingShip)

        // test
        // add garbage to tile
        val garbage = Garbage(1, 200, GarbageType.OIL, emptySet())
        val garbageTile = Sea.getTileByPos(Vec2D(6,6))
        garbageTile?.garbage = listOf(garbage)

        c1.run(emptyList())

        assert(scoutingShip.position == garbageTile)
    }
}
