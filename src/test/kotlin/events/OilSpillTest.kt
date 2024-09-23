package events

import corporation.UTFactory
import de.unisaarland.cs.se.selab.events.OilSpill
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OilSpillTest {
    var sea = Sea()
    private val factory = UTFactory()

    @BeforeEach
    fun setUp() {
        factory.createTestingMap()
        sea = factory.sea
    }

    @Test
    public fun testBasicCurrentTickIsNotFireTick() {
        val oilSpillEvent = OilSpill(1, 5, sea, DeepOcean(0, Vec2D(3, 5), emptyList(), emptyList(), null), 1, 500)
        assertTrue(!oilSpillEvent.actUponTick(4))
        assertTrue(sea.getTileByPos(Vec2D(3, 5))?.currentOilLevel() == 0)
        assertTrue(sea.getTileByPos(Vec2D(3, 4))?.currentOilLevel() == 0)
        assertTrue(sea.getTileByPos(Vec2D(2, 4))?.currentOilLevel() == 0)
        assertTrue(sea.getTileByPos(Vec2D(2, 5))?.currentOilLevel() == 0)
        assertTrue(sea.getTileByPos(Vec2D(2, 6))?.currentOilLevel() == 0)
        assertTrue(sea.getTileByPos(Vec2D(3, 6))?.currentOilLevel() == 0)

        val oilSpillEvent2 = OilSpill(1, 5, sea, DeepOcean(0, Vec2D(3, 5), emptyList(), emptyList(), null), 1, 500)
        assertTrue(oilSpillEvent2.actUponTick(5))
        assertTrue(sea.getTileByPos(Vec2D(3, 5))?.currentOilLevel() == 500)
        assertTrue(sea.getTileByPos(Vec2D(3, 4))?.currentOilLevel() == 500)
        assertTrue(sea.getTileByPos(Vec2D(2, 4))?.currentOilLevel() == 500)
        assertTrue(sea.getTileByPos(Vec2D(2, 5))?.currentOilLevel() == 500)
        assertTrue(sea.getTileByPos(Vec2D(2, 6))?.currentOilLevel() == 500)
        assertTrue(sea.getTileByPos(Vec2D(3, 6))?.currentOilLevel() == 500)

        val oilSpillEvent3 = OilSpill(1, 5, sea, DeepOcean(0, Vec2D(3, 5), emptyList(), emptyList(), null), 1, 500)
        sea.getTileByPos(Vec2D(2, 4))?.addGarbage(Garbage(0, 800, GarbageType.OIL, null))
        assertTrue(oilSpillEvent3.actUponTick(5))
        assertTrue(sea.getTileByPos(Vec2D(3, 5))?.currentOilLevel() == 1000)
        assertTrue(sea.getTileByPos(Vec2D(3, 4))?.currentOilLevel() == 1000)
        assertTrue(sea.getTileByPos(Vec2D(2, 4))?.currentOilLevel() == 1000)
        assertTrue(sea.getTileByPos(Vec2D(2, 5))?.currentOilLevel() == 1000)
        assertTrue(sea.getTileByPos(Vec2D(2, 6))?.currentOilLevel() == 1000)
        assertTrue(sea.getTileByPos(Vec2D(3, 6))?.currentOilLevel() == 1000)
    }
}
