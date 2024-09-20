package corporation

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ScoutingShipsTest {
    private val factory = UTFactory()

    @BeforeEach
    fun setUp() {
        Sea.tiles.clear()
        Sea.tileIndex = emptyMap()
        factory.createTestingMap()
    }

    /**
     * test a simple scouting ship movement to a tile with garbage in its visibility range
     * */
    @Test
    fun testMoveToGarbageSimple() {
        // test setup
        val shipTile = Sea.getTileByPos(Vec2D(5, 7)) ?: error("Tile not found at position (5,7)")
        val harborTile = Sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), emptyList<Task>())
        val capability = ScoutingShip(5)
        val scoutingShip = Ship(1, 100, 25, 3000, 10, mutableListOf(capability))
        scoutingShip.owner = c1
        scoutingShip.position = shipTile
        scoutingShip.name = "unknown"
        c1.ownedShips.add(scoutingShip)

        // test
        // add garbage to tile
        val garbage = Garbage(1, 200, GarbageType.OIL, emptySet())
        val garbageTile = Sea.getTileByPos(Vec2D(6, 6))
        garbageTile?.garbage = listOf(garbage)

        c1.run(emptyList())

        // test ship moved to garbage tile
        assert(scoutingShip.position == garbageTile)

        c1.run(emptyList())

        // assure scouting ship didn't leave tile in next tick
        assert(scoutingShip.position == garbageTile)
    }

    @Test
    fun testMoveToTwoGarbagePiles() {
        // setup
        val listOfShips = factory.createShips()
        val shipTile1 = Sea.getTileByPos(Vec2D(2, 6)) ?: error("Tile not found at position (2,6)")
        val shipTile2 = Sea.getTileByPos(Vec2D(6, 8)) ?: error("Tile not found at position (6,8)")
        val harborTile = Sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), emptyList<Task>())

        val scoutingShip1 = listOfShips[1]
        scoutingShip1.position = shipTile1
        scoutingShip1.owner = c1

        val scoutingShip2 = listOfShips[2]
        scoutingShip2.position = shipTile2
        scoutingShip2.owner = c1

        c1.ownedShips.add(scoutingShip1)
        c1.ownedShips.add(scoutingShip2)

        // test
        // add garbage to tile
        val garbage = Garbage(1, 200, GarbageType.OIL, emptySet())
        val garbageTile1 = Sea.getTileByPos(Vec2D(3, 4))
        val garbageTile2 = Sea.getTileByPos(Vec2D(4, 6))

        garbageTile1?.garbage = listOf(garbage)
        garbageTile2?.garbage = listOf(garbage)

        c1.run(emptyList())
        // test same distance of garbage piles
        assert(scoutingShip1.position == garbageTile1)
        // test ship moved to tile in FoV of Corporation
        assert(scoutingShip2.position == garbageTile2)

        c1.run(emptyList())
        // test ships remain at tile with garbage
        assert(scoutingShip1.position == garbageTile1)
        assert(scoutingShip2.position == garbageTile2)
    }

    @Test
    fun testMoveToCloseGarbageTwo() {
        // setup
        val listOfShips = factory.createShips()
        val shipTile1 = Sea.getTileByPos(Vec2D(2, 6)) ?: error("Tile not found at position (2,6)")
        val harborTile = Sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), emptyList<Task>())

        val scoutingShip1 = listOfShips[1]
        scoutingShip1.position = shipTile1
        scoutingShip1.owner = c1

        c1.ownedShips.add(scoutingShip1)

        // test
        // add garbage to tile
        val garbage = Garbage(1, 200, GarbageType.OIL, emptySet())
        val garbageTile1 = Sea.getTileByPos(Vec2D(3, 3))
        val garbageTile2 = Sea.getTileByPos(Vec2D(6, 6))

        garbageTile1?.garbage = listOf(garbage)
        garbageTile2?.garbage = listOf(garbage)

        c1.run(emptyList())
        // test ship move to the closest garbage pile
        assert(scoutingShip1.position == garbageTile1)

        c1.run(emptyList())

        // test ships remain at tile with garbage
        assert(scoutingShip1.position == garbageTile1)
    }

    @Test
    fun testMoveToCloseGarbageMultiple() {
        // setup
        val listOfShips = factory.createShips()
        val harborTile = Sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), emptyList<Task>())

        val shipTile1 = Sea.getTileByPos(Vec2D(2, 6)) ?: error("Tile not found at position (2,6)")
        val shipTile2 = Sea.getTileByPos(Vec2D(4, 7)) ?: error("Tile not found at position (2,6)")
        val shipTile3 = Sea.getTileByPos(Vec2D(1, 2)) ?: error("Tile not found at position (2,6)")
        val shipTile4 = Sea.getTileByPos(Vec2D(5, 6)) ?: error("Tile not found at position (2,6)")
        val shipTile5 = Sea.getTileByPos(Vec2D(6, 3)) ?: error("Tile not found at position (2,6)")

        val scoutingShip1 = listOfShips[1]
        scoutingShip1.position = shipTile1
        scoutingShip1.owner = c1

        val scoutingShip2 = listOfShips[2]
        scoutingShip2.position = shipTile2
        scoutingShip2.owner = c1

        val scoutingShip3 = listOfShips[3]
        scoutingShip3.position = shipTile3
        scoutingShip3.owner = c1

        val scoutingShip4 = listOfShips[4]
        scoutingShip4.position = shipTile4
        scoutingShip4.owner = c1

        val scoutingShip5 = listOfShips[5]
        scoutingShip5.position = shipTile5
        scoutingShip5.owner = c1

        c1.ownedShips.add(scoutingShip1)
        c1.ownedShips.add(scoutingShip2)
        c1.ownedShips.add(scoutingShip3)
        c1.ownedShips.add(scoutingShip4)
        c1.ownedShips.add(scoutingShip5)

        // test
        // add garbage to tile
        val garbage = Garbage(1, 200, GarbageType.OIL, emptySet())
        val garbageTile1 = Sea.getTileByPos(Vec2D(3, 3))
        val garbageTile2 = Sea.getTileByPos(Vec2D(6, 6))
        val garbageTile3 = Sea.getTileByPos(Vec2D(6, 3))
        val garbageTile4 = Sea.getTileByPos(Vec2D(5, 3))
        val garbageTile5 = Sea.getTileByPos(Vec2D(5, 7))
        val garbageTile6 = Sea.getTileByPos(Vec2D(7, 3))
        val garbageTile7 = Sea.getTileByPos(Vec2D(2, 4))

        garbageTile1?.garbage = listOf(garbage)
        garbageTile2?.garbage = listOf(garbage)
        garbageTile3?.garbage = listOf(garbage)
        garbageTile4?.garbage = listOf(garbage)
        garbageTile5?.garbage = listOf(garbage)
        garbageTile6?.garbage = listOf(garbage)
        garbageTile7?.garbage = listOf(garbage)

        c1.run(emptyList())
        // test ship move to the closest garbage pile
        assert(scoutingShip1.position == garbageTile7)
        assert(scoutingShip2.position == garbageTile5)
        assert(scoutingShip3.position == garbageTile1)
        assert(scoutingShip4.position == garbageTile2)
        assert(scoutingShip5.position == garbageTile3)

        c1.run(emptyList())

        // test ships remain at tile with garbage
        assert(scoutingShip1.position == garbageTile1)
        assert(scoutingShip2.position == garbageTile5)
        assert(scoutingShip3.position == garbageTile1)
        assert(scoutingShip4.position == garbageTile2)
        assert(scoutingShip5.position == garbageTile3)
    }
}
