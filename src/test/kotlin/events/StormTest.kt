package test.kotlin.events

import corporation.UTFactory
import de.unisaarland.cs.se.selab.events.Storm
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class StormTest {
    var factory = UTFactory()
    lateinit var sea: Sea

    @BeforeEach
    fun setUp() {
        factory.createTestingMap()
        sea = factory.sea
        sea.getTileByPos(Vec2D(6, 6))?.addGarbage(
            Garbage(1, 1000, GarbageType.OIL, emptySet())
        )
    }

    @Test
    fun testStorm() {
        val storm = Storm(
            1,
            0,
            sea,
            sea.getTileByPos(Vec2D(6, 6))!!,
            0,
            30
        )
        storm.direction = Direction.D180
        assertTrue(storm.actUponTick(0, emptyList()))
        assertTrue(sea.getTileByPos(Vec2D(3, 6))!!.currentOilLevel() == 1000)
    }
}
