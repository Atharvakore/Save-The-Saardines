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
            Vec2D(3, 7),
            Vec2D(2, 7),
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

    @Test
    fun testGetTilesInRadius2() {
        val currPosition: Vec2D = Vec2D(2, 7)
        val radius: Int = 2
        val tilesInRadius: Iterator<Vec2D> = currPosition.tilesInRadius(radius)

        val actualTilesInRadius: MutableList<Vec2D> = mutableListOf()
        tilesInRadius.forEach {
            actualTilesInRadius.add(it)
        }

        val expectedTilesInRadius: List<Vec2D> = listOf(
            Vec2D(2, 6),
            Vec2D(1, 6),
            Vec2D(1, 7),
            Vec2D(1, 8),
            Vec2D(2, 8),
            Vec2D(3, 7),
            Vec2D(2, 7),

            Vec2D(2, 5),
            Vec2D(1, 5),
            Vec2D(0, 6),
            Vec2D(0, 7),
            Vec2D(0, 8),
            Vec2D(1, 9),
            Vec2D(2, 9),
            Vec2D(3, 8),
            Vec2D(3, 6),
            Vec2D(3, 9),
            Vec2D(4, 7),
            Vec2D(3, 5),
        )

        assert(
            expectedTilesInRadius.containsAll(actualTilesInRadius) &&
                actualTilesInRadius.size == expectedTilesInRadius.size
        )
    }
}
