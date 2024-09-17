package tiles

import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TileTest {

    lateinit var sea: Sea

    @BeforeEach
    fun setup() {
        sea.tiles = listOf(
            Shore(1, Vec2D(0, 0), listOf(), listOf(), false),
            Shore(2, Vec2D(0, 1), listOf(), listOf(), false),
            DeepOcean(3, Vec2D(1, 0), listOf(), listOf(), Current(1, Direction.D60, 1)),
            DeepOcean(4, Vec2D(1, 1), listOf(), listOf(), Current(1, Direction.D60, 1)),
            ShallowOcean(5, Vec2D(2, 0), listOf(), listOf()),
            ShallowOcean(6, Vec2D(2, 1), listOf(), listOf())
        )
    }

    /** Testing basic functionalities **/

    @Test
    fun testGetTileInDirection() {
    }

    @Test
    fun testAddGarbage() {
        TODO("Not yet implemented")
    }

    @Test
    fun testGetAmountOfType() {
        TODO("Not yet implemented")
    }

    @Test
    fun testRemoveAmountOfType() {
        TODO("Not yet implemented")
    }

    @Test
    fun testCurrentOilLevel() {
        TODO("Not yet implemented")
    }
}
