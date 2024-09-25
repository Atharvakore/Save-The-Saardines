package tiles

import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeepOceanTest {

    lateinit var deepOcean: DeepOcean

    @BeforeEach
    fun setup() {
        val adjacentTiles: List<Tile?> = listOf(
            null,
            null,
            null,
            null,
            null,
            null,
        )
        deepOcean = DeepOcean(1, Vec2D(3, 3), adjacentTiles, mutableListOf(), Current(1, Direction.D60, 1))
    }

    /** Testing basic functionalities **/
    @Test
    fun testGetCurrent() {
        assert(deepOcean.getCurrent() == Current(1, Direction.D60, 1))
    }
}
