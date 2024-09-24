package corporation

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CorporationMoveShipsTest {
    private val factory = UTFactory()
    lateinit var sea: Sea

    @BeforeEach
    fun setUp() {
        factory.createTestingMap()
        sea = factory.sea
    }

    @Test
    fun testMoveShipsGeneral() {
        // setUP
        val harborTile = sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), mutableListOf())
        val c2 = Corporation(2, "c2", mutableListOf(), emptyList(), emptyList(), mutableListOf())

        val c2ShipTile = sea.getTileByPos(Vec2D(6, 2)) ?: error("Tile not found at position (6,2)")

        val scoutingShipTile = sea.getTileByPos(Vec2D(2, 6)) ?: error("Tile not found at position (2,6)")
        val coordinatingShipTile = sea.getTileByPos(Vec2D(5, 2)) ?: error("Tile not found at position (5,2)")
        val collectingShipTile = sea.getTileByPos(Vec2D(4, 2)) ?: error("Tile not found at position (4,2)")

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

        val garbageTile1 = sea.getTileByPos(Vec2D(4, 6))
        val garbage1 = Garbage(1, 200, GarbageType.OIL, emptySet())
        garbageTile1?.garbage = listOf(garbage1)

        // test ships movement after one tick
        c1.run(0, sea, otherShips = listOf(c2Ship))

        assert(scoutingShip.position == garbageTile1)
        assert(coordinatingShip.position == c2ShipTile)
        assert(collectingShip.position != garbageTile1)

        c2.run(0, sea, otherShips = listOf(scoutingShip, coordinatingShip, collectingShip))

        assert(c2Ship.position == c2ShipTile)
    }

    /**
     * test a simple scouting and collecting ships movement to a tile with garbage in its visibility range
     * */
    @Test
    fun testMoveCollectingShipToGarbageSimple() {
        // test setup
        val listOfShips = factory.createShips()
        val scoutingShipTile = sea.getTileByPos(Vec2D(5, 7)) ?: error("Tile not found at position (5,7)")
        val collectingShipTile = sea.getTileByPos(Vec2D(1, 1)) ?: error("Tile not found at position (5,7)")
        val harborTile = sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), mutableListOf())

        val scoutingShip = listOfShips[1]
        scoutingShip.owner = c1
        scoutingShip.position = scoutingShipTile
        scoutingShip.name = "unknown"
        c1.ownedShips.add(scoutingShip)

        val collectingShip = listOfShips[11]
        collectingShip.owner = c1
        collectingShip.position = collectingShipTile
        collectingShip.name = "unknown"
        c1.ownedShips.add(collectingShip)
        // test
        // add garbage to tile
        val garbage = Garbage(1, 200, GarbageType.OIL, emptySet())
        val garbageTile = sea.getTileByPos(Vec2D(8, 8))
        garbageTile?.garbage = listOf(garbage)

        c1.run(0, sea, emptyList())
        // test ship moved to garbage tile
        assert(scoutingShip.position != garbageTile)
        assert(collectingShip.position != garbageTile)

        c1.run(0, sea, emptyList())
        // assure scouting ship didn't leave tile in next tick and collecting ship arrived
        assert(scoutingShip.position == garbageTile)
        assert(collectingShip.position != garbageTile)
    }

    @Test
    fun testCollectingShipNotMoving() {
        // test setup
        val listOfShips = factory.createShips()
        val collectingShipTile = sea.getTileByPos(Vec2D(2, 2)) ?: error("Tile not found at position (5,7)")
        val c1 = Corporation(1, "c1", mutableListOf(), emptyList(), emptyList(), mutableListOf())

        val collectingShip = listOfShips[11]
        collectingShip.owner = c1
        collectingShip.position = collectingShipTile
        collectingShip.name = "unknown"
        c1.ownedShips.add(collectingShip)

        // test
        // add garbage to tile
        val garbage = Garbage(1, 200, GarbageType.OIL, emptySet())
        val garbageTile = sea.getTileByPos(Vec2D(8, 8))
        garbageTile?.garbage = listOf(garbage)

        // test no move when garbage away
        c1.run(0, sea, emptyList())
        assert(collectingShip.position == collectingShipTile)

        val garbage2 = Garbage(2, 200, GarbageType.OIL, emptySet())
        val garbageTile2 = sea.getTileByPos(Vec2D(2, 3))
        garbageTile2?.garbage = listOf(garbage2)

        // test no move when garbage close
        c1.run(0, sea, emptyList())
        assert(collectingShip.position == collectingShipTile)
    }

    @Test
    fun testCollectingShipBackToHarbor() {
        // test setup
        val listOfShips = factory.createShips()
        val scoutingShipTile = sea.getTileByPos(Vec2D(5, 7)) ?: error("Tile not found at position (5,7)")
        val collectingShipTile = sea.getTileByPos(Vec2D(1, 1)) ?: error("Tile not found at position (5,7)")
        val harborTile = sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), mutableListOf())

        val scoutingShip = listOfShips[1]
        scoutingShip.owner = c1
        scoutingShip.position = scoutingShipTile
        scoutingShip.name = "unknown"
        c1.ownedShips.add(scoutingShip)

        val container = Container(GarbageType.OIL, 100)
        container.garbageLoad = 100
        val collecting = CollectingShip(mutableListOf(container))
        val collectingShip = Ship(3, 40, 10, 3000, 10, mutableListOf(collecting))
        collectingShip.owner = c1
        collectingShip.position = collectingShipTile
        collectingShip.name = "unknown"
        c1.ownedShips.add(collectingShip)

        // test
        // add garbage to tile
        val garbage = Garbage(1, 200, GarbageType.OIL, emptySet())
        val garbageTile = sea.getTileByPos(Vec2D(4, 6))
        garbageTile?.garbage = listOf(garbage)

        c1.run(0, sea, emptyList())
        // test ship moved to garbage tile
        assert(scoutingShip.position == garbageTile)
        assert(collectingShip.position != harborTile)

        c1.run(0, sea, emptyList())
        // assure scouting ship didn't leave tile in next tick and collecting ship arrived
        assert(scoutingShip.position == garbageTile)
        assert(collectingShip.position != garbageTile)
    }
}
