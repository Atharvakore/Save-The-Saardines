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

    private var factory = UTFactory()

    @BeforeEach
    fun setUp() {
        Sea.tiles.clear()
        Sea.tileIndex = emptyMap()
        factory.createTestingMap()
    }

    @Test
    public fun testBasicCurrentTickIsNotFireTick() {
        val oilSpillEvent = OilSpill(1, 5, Sea, DeepOcean(0, Vec2D(3, 5), listOf(), listOf(), null), 1, 500)
        assertTrue(!oilSpillEvent.actUponTick(4))
        assertTrue(Sea.getTileByPos(Vec2D(3, 5))?.currentOilLevel() == 0)
        assertTrue(Sea.getTileByPos(Vec2D(3, 4))?.currentOilLevel() == 0)
        assertTrue(Sea.getTileByPos(Vec2D(2, 4))?.currentOilLevel() == 0)
        assertTrue(Sea.getTileByPos(Vec2D(2, 5))?.currentOilLevel() == 0)
        assertTrue(Sea.getTileByPos(Vec2D(2, 6))?.currentOilLevel() == 0)
        assertTrue(Sea.getTileByPos(Vec2D(3, 6))?.currentOilLevel() == 0)
    }

    @Test
    fun testBasicCurrentTickIsFireTick() {
        val oilSpillEvent = OilSpill(1, 5, Sea, DeepOcean(0, Vec2D(3, 5), listOf(), listOf(), null), 1, 500)
        assertTrue(oilSpillEvent.actUponTick(5))
        assertTrue(Sea.getTileByPos(Vec2D(3, 5))?.currentOilLevel() == 500)
        assertTrue(Sea.getTileByPos(Vec2D(3, 4))?.currentOilLevel() == 500)
        assertTrue(Sea.getTileByPos(Vec2D(2, 4))?.currentOilLevel() == 500)
        assertTrue(Sea.getTileByPos(Vec2D(2, 5))?.currentOilLevel() == 500)
        assertTrue(Sea.getTileByPos(Vec2D(2, 6))?.currentOilLevel() == 500)
        assertTrue(Sea.getTileByPos(Vec2D(3, 6))?.currentOilLevel() == 500)
    }

    @Test
    fun aTileWith800Oil() {
        val oilSpillEvent = OilSpill(1, 5, Sea, DeepOcean(0, Vec2D(3, 5), listOf(), listOf(), null), 1, 500)
        Sea.getTileByPos(Vec2D(2, 4))?.addGarbage(Garbage(0, 800, GarbageType.OIL, null))
        assertTrue(oilSpillEvent.actUponTick(5))
        assertTrue(Sea.getTileByPos(Vec2D(3, 5))?.currentOilLevel() == 500)
        assertTrue(Sea.getTileByPos(Vec2D(3, 4))?.currentOilLevel() == 500)
        assertTrue(Sea.getTileByPos(Vec2D(2, 4))?.currentOilLevel() == 1000)
        assertTrue(Sea.getTileByPos(Vec2D(2, 5))?.currentOilLevel() == 500)
        assertTrue(Sea.getTileByPos(Vec2D(2, 6))?.currentOilLevel() == 500)
        assertTrue(Sea.getTileByPos(Vec2D(3, 6))?.currentOilLevel() == 500)
    }
}
