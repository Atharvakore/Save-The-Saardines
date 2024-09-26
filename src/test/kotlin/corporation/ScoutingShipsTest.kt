package corporation

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ScoutingShipsTest {
    private val factory = UTFactory()
    var sea = Sea()

    @BeforeEach
    fun setUp() {
        factory.createTestingMap()
        sea = factory.sea
    }

    /**
     * test a simple scouting ship movement to a tile with garbage in its visibility range
     * */
    @Test
    fun testMoveToGarbageSimple() {
        // test setup
        val shipTile = sea.getTileByPos(Vec2D(5, 7)) ?: error("Tile not found at position (5,7)")
        val harborTile = sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), mutableListOf())
        val capability = ScoutingShip(5)
        val scoutingShip = Ship(1, 100, 25, 3000, 10, mutableListOf(capability))
        scoutingShip.owner = c1
        scoutingShip.position = shipTile
        scoutingShip.name = "unknown"
        c1.ownedShips.add(scoutingShip)

        // test
        // add garbage to tile
        val garbage = Garbage(1, 200, GarbageType.OIL, emptySet())
        val garbageTile = sea.getTileByPos(Vec2D(6, 6))
        garbageTile?.garbage = mutableListOf(garbage)

        // c1.run(0, sea, emptyList())
//
        // // test ship moved to garbage tile
        // assert(scoutingShip.position == garbageTile)
//
        // c1.run(0, sea, emptyList())
//
        // // assure scouting ship didn't leave tile in next tick
        // assert(scoutingShip.position == garbageTile)
    } //

    @Test
    fun testMoveToTwoGarbagePiles() {
        // setup
        val listOfShips = factory.createShips()
        val shipTile1 = sea.getTileByPos(Vec2D(2, 6)) ?: error("Tile not found at position (2,6)")
        val shipTile2 = sea.getTileByPos(Vec2D(6, 8)) ?: error("Tile not found at position (6,8)")
        val harborTile = sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), mutableListOf())

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
        val garbage1 = Garbage(1, 200, GarbageType.OIL, emptySet())
        val garbage2 = Garbage(2, 200, GarbageType.OIL, emptySet())
        val garbageTile1 = sea.getTileByPos(Vec2D(3, 4))
        val garbageTile2 = sea.getTileByPos(Vec2D(4, 6))

        garbageTile1?.garbage = mutableListOf(garbage1)
        garbageTile2?.garbage = mutableListOf(garbage2)

        // c1.run(0, sea, emptyList())
        // // test same distance of garbage piles
        // assert(scoutingShip1.position == garbageTile1)
        // // test ship moved to tile in FoV of Corporation
        // assert(scoutingShip2.position != garbageTile2)
//
        // c1.run(0, sea, emptyList())
        // // test ships remain at tile with garbage
        // assert(scoutingShip1.position == garbageTile1)
        // assert(scoutingShip2.position == garbageTile2)
    }

    @Test
    fun testMoveToCloseGarbageTwo() {
        // setup
        val listOfShips = factory.createShips()
        val shipTile1 = sea.getTileByPos(Vec2D(2, 6)) ?: error("Tile not found at position (2,6)")
        val harborTile = sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), mutableListOf())

        val scoutingShip1 = listOfShips[1]
        scoutingShip1.position = shipTile1
        scoutingShip1.owner = c1

        c1.ownedShips.add(scoutingShip1)

        // test
        // add garbage to tile
        val garbage1 = Garbage(1, 200, GarbageType.OIL, emptySet())
        val garbage2 = Garbage(2, 200, GarbageType.OIL, emptySet())

        val garbageTile1 = sea.getTileByPos(Vec2D(3, 3))
        val garbageTile2 = sea.getTileByPos(Vec2D(6, 6))

        garbageTile1?.garbage = mutableListOf(garbage1)
        garbageTile2?.garbage = mutableListOf(garbage2)

        // c1.run(0, sea, emptyList())
        // // test ship move to the closest garbage pile
        // assert(scoutingShip1.position != garbageTile1)
//
        // c1.run(0, sea, emptyList())
//
        // // test ships remain at tile with garbage
        // assert(scoutingShip1.position == garbageTile1)
    }
}
