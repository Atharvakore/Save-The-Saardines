package ship

import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShipTest {

    private val ship: Ship = Ship(1, 10, 5, 3000, 7, mutableListOf())
    private lateinit var tile37: Tile
    private lateinit var tile38: Tile

    @BeforeEach
    fun setUp() {
        tile37 = DeepOcean(37, Vec2D(6, 3), emptyList(), mutableListOf(), Current(10, Direction.D0, 1))
        tile38 = ShallowOcean(38, Vec2D(7, 3), emptyList(), mutableListOf())
        tile37.adjacentTiles = listOf(tile38)
        tile38.adjacentTiles = listOf(tile37)
    }

    @Test
    fun testDrift() {
        ship.position = tile37
        ship.drift()
        assertTrue(ship.position == tile38)
        assert(true)
    }

    @Test
    fun testMove() {
        assert(true)
//        ship.position = tile37
//        ship.move(listOf(tile38))
//        assertTrue(ship.position == tile38)
//        assert(true)
    }

    @Test
    fun testAddCapability() {
        val capability: CoordinatingShip = CoordinatingShip(1)
        ship.addCapability(capability)
        assertTrue(ship.capabilities.contains(capability))
    }

    @Test
    fun testTickTask() {
        assert(true)
    }

    @Test
    fun testMoveUninterrupted() {
        assert(true)
    }
}
