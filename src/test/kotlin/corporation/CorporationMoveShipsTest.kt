package corporation

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CorporationMoveShipsTest {
    private val factory = UTFactory()

    @BeforeEach
    fun setUp() {
        Sea.tiles.clear()
        Sea.tileIndex = emptyMap()
        factory.createTestingMap()
    }

    @Test
    fun testMoveShipsGeneral() {
        // setUP
        val harborTile = Sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), emptyList<Task>())
        val c2 = Corporation(2, "c2", mutableListOf(), emptyList(), listOf(), emptyList<Task>())

        val c2ShipTile = Sea.getTileByPos(Vec2D(6, 2)) ?: error("Tile not found at position (6,2)")

        val scoutingShipTile = Sea.getTileByPos(Vec2D(2, 6)) ?: error("Tile not found at position (2,6)")
        val coordinatingShipTile = Sea.getTileByPos(Vec2D(5, 2)) ?: error("Tile not found at position (5,2)")
        val collectingShipTile = Sea.getTileByPos(Vec2D(4, 2)) ?: error("Tile not found at position (4,2)")

        val listOfShips = factory.createShips()

        val c2Ship = listOfShips[12]
        c2Ship.position = c2ShipTile
        c2Ship.owner = c2

        val scoutingShip = listOfShips[1]
        scoutingShip.position = scoutingShipTile
        scoutingShip.owner = c1

        val coordinatingShip = listOfShips[6]
        coordinatingShip.position = coordinatingShipTile
        coordinatingShip.owner = c1

        val collectingShip = listOfShips[11]
        collectingShip.position = collectingShipTile
        collectingShip.owner = c1

        c1.ownedShips.add(scoutingShip)
        c1.ownedShips.add(coordinatingShip)
        c1.ownedShips.add(collectingShip)
        c2.ownedShips.add(c2Ship)

        val garbageTile1 = Sea.getTileByPos(Vec2D(4, 6))
        val garbage1 = Garbage(1, 200, GarbageType.OIL, emptySet())
        garbageTile1?.garbage = listOf(garbage1)

        // test ships movement after one tick
        c1.run(otherShips = listOf(c2Ship))

        assert(scoutingShip.position == garbageTile1)
        assert(coordinatingShip.position == c2ShipTile)
        assert(collectingShip.position == garbageTile1)

        c2.run(otherShips = listOf(scoutingShip, coordinatingShip, collectingShip))

        assert(c2Ship.position == c2ShipTile)
    }
}
