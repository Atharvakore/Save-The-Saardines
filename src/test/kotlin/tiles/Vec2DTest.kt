package tiles

import de.unisaarland.cs.se.selab.tiles.Vec2D
import kotlin.test.Test

class Vec2DTest {

    /** Testing basic functionalities **/

    @Test
    fun testGetTilesInRadius() {
        val currPosition: Vec2D = Vec2D(2, 7)
        val radius: Int = 1
        val tilesInRadius: Iterator<Vec2D> = currPosition.tilesInRadius(radius)
        val expectedTilesInRadius: List<Vec2D> = listOf(
            Vec2D(2, 6),
            Vec2D(1, 6),
            Vec2D(1, 7),
            Vec2D(1, 8),
            Vec2D(2, 8),
            Vec2D(3, 7)
        )

        val actualTilesInRadius: MutableList<Vec2D> = mutableListOf()
        tilesInRadius.forEach {
            actualTilesInRadius.add(it)
        }

        assert(
            expectedTilesInRadius.containsAll(actualTilesInRadius) &&
                actualTilesInRadius.size == expectedTilesInRadius.size
        )
    }
}
