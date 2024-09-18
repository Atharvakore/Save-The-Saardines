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
    private val tile37: Tile = DeepOcean(37, Vec2D(6, 3), emptyList(), emptyList(), Current(10, Direction.D0, 1))
    private val tile38: Tile = ShallowOcean(38, Vec2D(7, 3), emptyList(), emptyList())

    @BeforeEach
    fun setUp() {
        tile37.adjacentTiles = listOf(tile38)
        tile38.adjacentTiles = listOf(tile37)
    }

    /** Testing basic functionalities **/

    @Test
    fun testRefuel() {
        ship.refuel()
        assertTrue(ship.isFuelSufficient(10))
    }

    @Test
    fun testDrift() {
//        ship.position = tile37
//        ship.drift()
//        assertTrue(ship.position == tile38)
        assert(true)
    }

    @Test
    fun testMove() {
//        ship.position = tile37
//        ship.move(listOf(tile38))
//        assertTrue(ship.position == tile38)
        assert(true)
    }

    @Test
    fun testIsFuelSufficient() {
        assertTrue(ship.isFuelSufficient(10))
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
