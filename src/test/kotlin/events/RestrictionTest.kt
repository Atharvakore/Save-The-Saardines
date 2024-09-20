package events

import corporation.UTFactory
import de.unisaarland.cs.se.selab.events.EndRestriction
import de.unisaarland.cs.se.selab.events.Restriction
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class RestrictionTest {
    lateinit var ship: Ship
    private val factory = UTFactory()

    @BeforeEach
    fun setUp() {
        ship = Ship(1, 50, 15, 5000, 7, mutableListOf())
        Sea.tiles.clear()
        Sea.tileIndex = emptyMap()
        factory.createTestingMap()
    }

    @Test
    fun startRestrictionTest() {
        val restrictionEvent = Restriction(1, 1, Sea, Sea.getTileByPos(Vec2D(3, 6))!!, 2)
        val endRestriction = EndRestriction(2, 5, Sea, Sea.getTileByPos(Vec2D(3, 6))!!, 1)
        assertTrue(!restrictionEvent.actUponTick(0) && !endRestriction.actUponTick(0))
        assertTrue(restrictionEvent.actUponTick(1))
        assertTrue((Sea.getTileByPos(Vec2D(3, 5))?.restrictions ?: true) == 1)
        assertTrue((Sea.getTileByPos(Vec2D(3, 6))?.restrictions ?: true) == 1)
        assertTrue((Sea.getTileByPos(Vec2D(3, 4))?.restrictions ?: true) == 1)
        assertTrue((Sea.getTileByPos(Vec2D(3, 5))?.restrictions ?: true) == 1)
        assertTrue((Sea.getTileByPos(Vec2D(2, 4))?.restrictions ?: true) == 1)
        assertTrue((Sea.getTileByPos(Vec2D(2, 5))?.restrictions ?: true) == 1)
        assertTrue((Sea.getTileByPos(Vec2D(2, 6))?.restrictions ?: true) == 1)
        assertTrue((Sea.getTileByPos(Vec2D(3, 7))?.restrictions ?: true) == 1)
        assertTrue((Sea.getTileByPos(Vec2D(4, 7))?.restrictions ?: true) == 1)
        assertTrue((Sea.getTileByPos(Vec2D(5, 7))?.restrictions ?: true) == 1)
        assertTrue((Sea.getTileByPos(Vec2D(4, 6))?.restrictions ?: true) == 1)
        assertTrue((Sea.getTileByPos(Vec2D(5, 6))?.restrictions ?: true) == 1)
    }

    /**
     * Note Here StartRestriction has radius 2 but end one has 1 so end of test we should have tiles with Restriction
     */
    /**
     * Behaviour of Ships is yet to be tested
     */
    @Test
    fun endRestrictionTest() {
        val restrictionEvent = Restriction(1, 1, Sea, Sea.getTileByPos(Vec2D(3, 6))!!, 2)
        val endRestriction = EndRestriction(2, 5, Sea, Sea.getTileByPos(Vec2D(3, 6))!!, 2)
        assertTrue(!restrictionEvent.actUponTick(0) && !endRestriction.actUponTick(0))
        assert(restrictionEvent.actUponTick(1))
        assert(endRestriction.actUponTick(5))
        assertTrue((Sea.getTileByPos(Vec2D(3, 5))?.restrictions ?: true) == 0)
        assertTrue((Sea.getTileByPos(Vec2D(3, 6))?.restrictions ?: true) == 0)
        assertTrue((Sea.getTileByPos(Vec2D(3, 4))?.restrictions ?: true) == 0)
        assertTrue((Sea.getTileByPos(Vec2D(3, 5))?.restrictions ?: true) == 0)
        assertTrue((Sea.getTileByPos(Vec2D(2, 4))?.restrictions ?: true) == 0)
        assertTrue((Sea.getTileByPos(Vec2D(2, 5))?.restrictions ?: true) == 0)
        assertTrue((Sea.getTileByPos(Vec2D(2, 6))?.restrictions ?: true) == 0)
        assertTrue((Sea.getTileByPos(Vec2D(3, 7))?.restrictions ?: true) == 0)
        assertTrue((Sea.getTileByPos(Vec2D(4, 7))?.restrictions ?: true) == 0)
        assertTrue((Sea.getTileByPos(Vec2D(5, 7))?.restrictions ?: true) == 0)
        assertTrue((Sea.getTileByPos(Vec2D(4, 6))?.restrictions ?: true) == 0)
        assertTrue((Sea.getTileByPos(Vec2D(5, 6))?.restrictions ?: true) == 0)
    }
}
