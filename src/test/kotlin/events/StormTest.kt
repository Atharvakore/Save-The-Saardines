package test.kotlin.events

import corporation.UTFactory
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach

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
}
