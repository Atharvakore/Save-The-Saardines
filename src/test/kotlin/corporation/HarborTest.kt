package test.kotlin.corporation

import corporation.UTFactory
import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HarborTest {
    private val factory = UTFactory()
    var sea = Sea()

    @BeforeEach
    fun setUp() {
        factory.createTestingMap()
        sea = factory.sea
    }

    @Test
    fun testShipMoveToHarbor() {
        val shipTile1 = sea.getTileByPos(Vec2D(5, 3)) ?: error("Tile not found at position (5,7)")
        val shipTile2 = sea.getTileByPos(Vec2D(7, 7)) ?: error("Tile not found at position (5,7)")
        val harborTile1 = (sea.getTileByPos(Vec2D(4, 1)) ?: error("null assertion message")) as Shore
        val harborTile2 = (sea.getTileByPos(Vec2D(6, 1)) ?: error("null assertion message")) as Shore
        val harborTile3 = (sea.getTileByPos(Vec2D(8, 1)) ?: error("null assertion message")) as Shore

        val list = listOf(harborTile1, harborTile2, harborTile3)
        val gType = GarbageType.OIL

        val c1 = Corporation(1, "c1", mutableListOf(), list, listOf(gType), mutableListOf())

        val listOfShips = factory.createShips()
        val collectingShip1 = listOfShips[11]
        collectingShip1.owner = c1
        collectingShip1.position = shipTile1
        collectingShip1.name = "unknown"

        val collectingShip2 = listOfShips[10]
        collectingShip2.owner = c1
        collectingShip2.position = shipTile2
        collectingShip2.name = "unknown"

        c1.ownedShips.add(collectingShip1)
        c1.ownedShips.add(collectingShip2)

        (collectingShip1.capabilities.first() as CollectingShip).auxiliaryContainers.first().garbageLoad = 100000
        (collectingShip2.capabilities.first() as CollectingShip).auxiliaryContainers.first().garbageLoad = 100000

        val garbage1 = Garbage(1, 200, GarbageType.OIL, emptySet())
        shipTile1.garbage = mutableListOf(garbage1)

        val garbage2 = Garbage(2, 200, GarbageType.OIL, emptySet())
        shipTile2.garbage = mutableListOf(garbage2)

        // c1.run(0, sea, emptyList<Ship>())
//
        // assert(collectingShip1.position == sea.getTileByPos(Vec2D(4, 2)))
        // assert(collectingShip2.position == sea.getTileByPos(Vec2D(6, 6)))
//
        // c1.run(1, sea, emptyList<Ship>())
//
        // assert(collectingShip1.position == sea.getTileByPos(Vec2D(4, 1)))
        // assert(collectingShip2.position == sea.getTileByPos(Vec2D(6, 4)))

        // c1.run(1, sea, emptyList<Ship>())
//
        // assert(collectingShip2.position == sea.getTileByPos(Vec2D(6, 1)))
    }
}
