package events

import corporation.UTFactory
import de.unisaarland.cs.se.selab.events.OilSpill
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OilSpillTest {
    var sea = Sea()

    @BeforeEach
    fun setUp() {
        val factory = UTFactory()
        factory.createTestingMap()
        sea = factory.sea
    }

    @Test
    public fun testBasicCurrentTickIsNotFireTick() {
        val oilSpillEvent = OilSpill(1, 5, sea, DeepOcean(0, Vec2D(3, 5), emptyList(), emptyList(), null), 1, 500)
        assertTrue(!oilSpillEvent.actUponTick(4, emptyList()))
        assertTrue(sea.getTileByPos(Vec2D(3, 5))?.currentOilLevel() == 0)
        assertTrue(sea.getTileByPos(Vec2D(3, 4))?.currentOilLevel() == 0)
        assertTrue(sea.getTileByPos(Vec2D(2, 4))?.currentOilLevel() == 0)
        assertTrue(sea.getTileByPos(Vec2D(2, 5))?.currentOilLevel() == 0)
        assertTrue(sea.getTileByPos(Vec2D(2, 6))?.currentOilLevel() == 0)
        assertTrue(sea.getTileByPos(Vec2D(3, 6))?.currentOilLevel() == 0)

        val oilSpillEvent2 = OilSpill(1, 5, sea, DeepOcean(0, Vec2D(3, 5), emptyList(), emptyList(), null), 1, 500)
        assertTrue(oilSpillEvent2.actUponTick(5, emptyList()))
        assertTrue(sea.getTileByPos(Vec2D(3, 5))?.currentOilLevel() == 500)
        assertTrue(sea.getTileByPos(Vec2D(3, 4))?.currentOilLevel() == 500)
        assertTrue(sea.getTileByPos(Vec2D(2, 4))?.currentOilLevel() == 500)
        assertTrue(sea.getTileByPos(Vec2D(2, 5))?.currentOilLevel() == 500)
        assertTrue(sea.getTileByPos(Vec2D(2, 6))?.currentOilLevel() == 500)
        assertTrue(sea.getTileByPos(Vec2D(3, 6))?.currentOilLevel() == 500)

        val oilSpillEvent3 = OilSpill(1, 5, sea, DeepOcean(0, Vec2D(3, 5), emptyList(), emptyList(), null), 1, 500)
        sea.getTileByPos(Vec2D(2, 4))?.addGarbage(Garbage(0, 800, GarbageType.OIL, emptySet()))
        assertTrue(oilSpillEvent3.actUponTick(5, emptyList()))
        assertTrue(sea.getTileByPos(Vec2D(3, 5))?.currentOilLevel() == 1000)
        assertTrue(sea.getTileByPos(Vec2D(3, 4))?.currentOilLevel() == 1000)
        assertTrue(sea.getTileByPos(Vec2D(2, 4))?.currentOilLevel() == 1000)
        assertTrue(sea.getTileByPos(Vec2D(2, 5))?.currentOilLevel() == 1000)
        assertTrue(sea.getTileByPos(Vec2D(2, 6))?.currentOilLevel() == 1000)
        assertTrue(sea.getTileByPos(Vec2D(3, 6))?.currentOilLevel() == 1000)
    }

    @Test
    fun testSpillInRadius1() {
        val tile53 = sea.getTileById(53)!!
        val tile62 = sea.getTileById(62)!!
        val tile73 = sea.getTileById(73)!!
        val tile63 = sea.getTileById(63)!!
        tile53.addGarbage(Garbage(1, 200, GarbageType.OIL, emptySet()))
        tile62.addGarbage(Garbage(2, 1000, GarbageType.OIL, emptySet()))
        tile73.addGarbage(Garbage(3, 700, GarbageType.OIL, emptySet()))
        val oilSpillEvent = OilSpill(1, 0, sea, tile63, 1, 500)
        oilSpillEvent.actUponTick(0, emptyList())
        assertTrue(tile53.currentOilLevel() == 700)
        assertTrue(tile62.currentOilLevel() == 1000)
        assertTrue(tile73.currentOilLevel() == 1000)
    }
}
