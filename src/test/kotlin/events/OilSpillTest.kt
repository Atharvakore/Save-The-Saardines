package events

import de.unisaarland.cs.se.selab.events.OilSpill
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OilSpillTest {
    private val seaInstance: Sea = Sea
    private val oilSpillEvent = OilSpill(1, 5, Sea, DeepOcean(0, Vec2D(4, 5), listOf(), listOf(), null), 1, 500)

    @BeforeEach
    fun setUp() {
        val listOfTiles: MutableList<Tile> = mutableListOf(
            DeepOcean(0, Vec2D(4, 5), listOf(), listOf(), null),
            DeepOcean(1, Vec2D(5, 5), listOf(), listOf(), null),
            DeepOcean(2, Vec2D(5, 4), listOf(), listOf(), null),
            DeepOcean(3, Vec2D(4, 4), listOf(), listOf(), null),
            DeepOcean(4, Vec2D(3, 4), listOf(), listOf(), null),
            DeepOcean(5, Vec2D(3, 5), listOf(), listOf(), null),
            DeepOcean(6, Vec2D(3, 6), listOf(), listOf(), null),
            DeepOcean(7, Vec2D(4, 6), listOf(), listOf(), null),
        )
        Sea.tiles.addAll(listOfTiles)
    }

    @AfterEach
    fun cleanup() {
        for (i in 0..7) {
            Sea.getTileById(i)?.garbage = listOf()
        }
    }

    @Test
    fun testBasicCurrentTickIsNotFireTick() {
        this.setUp()
        assertTrue(!oilSpillEvent.actUponTick(4))
        assertTrue(seaInstance.getTileById(0)?.currentOilLevel() == 0)
        assertTrue(seaInstance.getTileById(1)?.currentOilLevel() == 0)
        assertTrue(seaInstance.getTileById(2)?.currentOilLevel() == 0)
        assertTrue(seaInstance.getTileById(3)?.currentOilLevel() == 0)
        assertTrue(seaInstance.getTileById(4)?.currentOilLevel() == 0)
        assertTrue(seaInstance.getTileById(5)?.currentOilLevel() == 0)
        assertTrue(seaInstance.getTileById(6)?.currentOilLevel() == 0)
        assertTrue(seaInstance.getTileById(7)?.currentOilLevel() == 0)
    }

    @Test
    fun testBasicCurrentTickIsFireTick() {
        assertTrue(oilSpillEvent.actUponTick(5))
        assertTrue(seaInstance.getTileById(0)?.currentOilLevel() == 500)
        assertTrue(seaInstance.getTileById(1)?.currentOilLevel() == 500)
        assertTrue(seaInstance.getTileById(2)?.currentOilLevel() == 500)
        assertTrue(seaInstance.getTileById(3)?.currentOilLevel() == 500)
        assertTrue(seaInstance.getTileById(4)?.currentOilLevel() == 0)
        assertTrue(seaInstance.getTileById(5)?.currentOilLevel() == 500)
        assertTrue(seaInstance.getTileById(6)?.currentOilLevel() == 500)
        assertTrue(seaInstance.getTileById(7)?.currentOilLevel() == 500)
    }

    @Test
    fun aTileWith800Oil() {
        seaInstance.getTileById(0)?.addGarbage(Garbage(0, 800, GarbageType.OIL, null))
        assertTrue(oilSpillEvent.actUponTick(5))
        assertTrue(seaInstance.getTileById(0)?.currentOilLevel() == 1000)
        assertTrue(seaInstance.getTileById(1)?.currentOilLevel() == 500)
        assertTrue(seaInstance.getTileById(2)?.currentOilLevel() == 500)
        assertTrue(seaInstance.getTileById(3)?.currentOilLevel() == 500)
        assertTrue(seaInstance.getTileById(4)?.currentOilLevel() == 0)
        assertTrue(seaInstance.getTileById(5)?.currentOilLevel() == 500)
        assertTrue(seaInstance.getTileById(6)?.currentOilLevel() == 500)
        assertTrue(seaInstance.getTileById(7)?.currentOilLevel() == 500)
    }
}
