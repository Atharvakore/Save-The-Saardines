package tiles

import de.unisaarland.cs.se.selab.tiles.Vec2D
import kotlin.test.Test

class Vec2DTest {

    /** Testing basic functionalities **/

    @Test
    fun testGetTilesInRadius() {
        val currPosition: Vec2D = Vec2D(1, 6)
        val radius: Int = 1
        val tilesInRadius: Iterator<Vec2D> = currPosition.tilesInRadius(radius)
        val expectedTilesInRadius: List<Vec2D> = listOf(
            Vec2D(1, 5),
            Vec2D(2, 5),
            Vec2D(2, 7),
            Vec2D(2, 6),
            Vec2D(1, 7),
            Vec2D(0, 6)
        )

        val actualTilesInRadius: List<Vec2D> = tilesInRadius.asSequence().toList()

        assert(
            expectedTilesInRadius.containsAll(actualTilesInRadius) &&
                actualTilesInRadius.size == expectedTilesInRadius.size
        )
    }
}
