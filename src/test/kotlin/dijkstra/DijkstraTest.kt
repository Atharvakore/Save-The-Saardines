package test.kotlin.dijkstra

import corporation.UTFactory
import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.tiles.Dijkstra
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DijkstraTest {
    private val factory = UTFactory()
    var sea = Sea()

    @BeforeEach
    fun setUp() {
        factory.createTestingMap()
        sea = factory.sea
    }

    @Test
    fun testMovePath() {
        // setUP
        val harborTile = sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), mutableListOf())
        val scoutingShipTile = sea.getTileByPos(Vec2D(2, 6)) ?: error("Tile not found at position (2,6)")
        val collectingShipTile = sea.getTileByPos(Vec2D(4, 2)) ?: error("Tile not found at position (4,2)")

        val listOfShips = factory.createShips()

        val scoutingShip = listOfShips[1]
        scoutingShip.position = scoutingShipTile
        scoutingShip.owner = c1

        val collectingShip = listOfShips[11]
        collectingShip.position = collectingShipTile
        collectingShip.owner = c1

        c1.ownedShips.add(scoutingShip)
        c1.ownedShips.add(collectingShip)

        val garbageTile1 = sea.getTileByPos(Vec2D(4, 6))
        val garbage1 = Garbage(1, 200, GarbageType.OIL, emptySet())
        garbageTile1?.garbage = mutableListOf(garbage1)

        // test ships movement after one tick
        // c1.run(0, sea, emptyList())
//
        // assert(scoutingShip.position == garbageTile1)
        // assert(collectingShip.position == sea.getTileByPos(Vec2D(4, 3)))
//
        // c1.run(0, sea, emptyList())
        // assert(scoutingShip.position == garbageTile1)
        // assert(collectingShip.position == sea.getTileByPos(Vec2D(3, 5)))
//
        // c1.run(0, sea, emptyList())
        // assert(scoutingShip.position == garbageTile1)
        // assert(collectingShip.position == garbageTile1)
    }

    @Test
    fun testMovePath4() {
        // setUP
        val harborTile = sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), mutableListOf())
        val scoutingShipTile = sea.getTileByPos(Vec2D(8, 2)) ?: error("Tile not found at position (2,6)")
        val collectingShipTile = sea.getTileByPos(Vec2D(5, 3)) ?: error("Tile not found at position (4,2)")

        val listOfShips = factory.createShips()

        val scoutingShip = listOfShips[1]
        scoutingShip.position = scoutingShipTile
        scoutingShip.owner = c1

        val collectingShip = listOfShips[11]
        collectingShip.position = collectingShipTile
        collectingShip.owner = c1

        c1.ownedShips.add(scoutingShip)
        c1.ownedShips.add(collectingShip)

        val garbageTile1 = sea.getTileByPos(Vec2D(8, 1))
        val garbage1 = Garbage(1, 200, GarbageType.OIL, emptySet())
        garbageTile1?.garbage = mutableListOf(garbage1)

        // test ships movement after one tick
        // c1.run(0, sea, emptyList())
//
        // assert(scoutingShip.position == garbageTile1)
        // assert(collectingShip.position == sea.getTileByPos(Vec2D(5, 2)))
//
        // c1.run(0, sea, emptyList())
        // assert(scoutingShip.position == garbageTile1)
        // assert(collectingShip.position == sea.getTileByPos(Vec2D(7, 1)))
//
        // c1.run(0, sea, emptyList())
        // assert(scoutingShip.position == garbageTile1)
        // assert(collectingShip.position == garbageTile1)
    }

    @Test
    fun testMovePath5() {
        // setUP
        val harborTile = sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), mutableListOf())
        val scoutingShipTile = sea.getTileByPos(Vec2D(7, 7)) ?: error("Tile not found at position (2,6)")
        val collectingShipTile = sea.getTileByPos(Vec2D(6, 2)) ?: error("Tile not found at position (4,2)")

        val listOfShips = factory.createShips()

        val scoutingShip = listOfShips[1]
        scoutingShip.position = scoutingShipTile
        scoutingShip.owner = c1

        val collectingShip = listOfShips[11]
        collectingShip.position = collectingShipTile
        collectingShip.owner = c1

        c1.ownedShips.add(scoutingShip)
        c1.ownedShips.add(collectingShip)

        val garbageTile1 = sea.getTileByPos(Vec2D(7, 7))
        val garbage1 = Garbage(1, 200, GarbageType.OIL, emptySet())
        garbageTile1?.garbage = mutableListOf(garbage1)

        // test ships movement after one tick
        // c1.run(0, sea, emptyList())
//
        // assert(scoutingShip.position == garbageTile1)
        // assert(collectingShip.position == sea.getTileByPos(Vec2D(6, 3)))
//
        // c1.run(0, sea, emptyList())
        // assert(scoutingShip.position == garbageTile1)
        // assert(collectingShip.position == sea.getTileByPos(Vec2D(6, 5)))
//
        // c1.run(0, sea, emptyList())
        // assert(scoutingShip.position == garbageTile1)
        // assert(collectingShip.position == garbageTile1)
    }

    @Test
    fun testMovePath7() {
        // setUP
        val harborTile = sea.getTileByPos(Vec2D(1, 5)) ?: error("Tile not found at position (1,5)")
        val gType = GarbageType.OIL
        val c1 = Corporation(1, "c1", mutableListOf(), listOf(harborTile as Shore), listOf(gType), mutableListOf())
        val scoutingShipTile = sea.getTileByPos(Vec2D(1, 2)) ?: error("Tile not found at position (2,6)")
        val collectingShipTile = sea.getTileByPos(Vec2D(3, 7)) ?: error("Tile not found at position (4,2)")

        val listOfShips = factory.createShips()

        val scoutingShip = listOfShips[1]
        scoutingShip.position = scoutingShipTile
        scoutingShip.owner = c1

        val collectingShip = listOfShips[11]
        collectingShip.position = collectingShipTile
        collectingShip.owner = c1

        c1.ownedShips.add(scoutingShip)
        c1.ownedShips.add(collectingShip)

        val garbageTile1 = sea.getTileByPos(Vec2D(1, 2))
        val garbage1 = Garbage(1, 200, GarbageType.OIL, emptySet())
        garbageTile1?.garbage = mutableListOf(garbage1)

        // test ships movement after one tick
        // c1.run(0, sea, emptyList())
//
        // assert(scoutingShip.position == garbageTile1)
        // assert(collectingShip.position == sea.getTileByPos(Vec2D(2, 6)))
//
        // c1.run(0, sea, emptyList())
        // assert(scoutingShip.position == garbageTile1)
        // assert(collectingShip.position == sea.getTileByPos(Vec2D(1, 4)))
//
        // c1.run(0, sea, emptyList())
        // assert(scoutingShip.position == garbageTile1)
        // assert(collectingShip.position == garbageTile1)
    }

    @Test
    fun testDijkstra() {
        val start = sea.getTileByPos(Vec2D(3, 2))
        val paths: List<Tile>
        if (start != null) {
            paths = sea.getTileByPos(Vec2D(8, 5))?.let { Dijkstra(start).shortestPathTo(it) }!!
            assert(paths[1] == sea.getTileByPos(Vec2D(4, 2)))
            assert(paths[2] == sea.getTileByPos(Vec2D(5, 2)))
            assert(paths[3] == sea.getTileByPos(Vec2D(6, 2)))
            assert(paths[4] == sea.getTileByPos(Vec2D(7, 3)))
            assert(paths[5] == sea.getTileByPos(Vec2D(7, 4)))
            assert(paths[6] == sea.getTileByPos(Vec2D(8, 5)))
        }
    }
}
