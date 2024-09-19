package test.kotlin.corporation

import corporation.UTFactory
import de.unisaarland.cs.se.selab.tiles.Sea
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CoordinatingShipsMoveTest {
    private val factory = UTFactory()

    @BeforeEach
    fun setUp() {
        Sea.tiles.clear()
        Sea.tileIndex = emptyMap()
        factory.createTestingMap()
    }

    @Test
    fun example() {
        assert(true)
    }
}
