package tiles

import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class SeaTest {

    @BeforeEach
    fun setUp() {
        val listOfTiles: MutableList<Tile> = mutableListOf(
            Shore(1, Vec2D(0, 0), emptyList(), emptyList(), false),
            Shore(2, Vec2D(0, 1), emptyList(), emptyList(), false),
            DeepOcean(3, Vec2D(1, 0), emptyList(), emptyList(), Current(1, Direction.D60, 1)),
            DeepOcean(4, Vec2D(1, 1), emptyList(), emptyList(), Current(1, Direction.D60, 1)),
            ShallowOcean(5, Vec2D(2, 0), emptyList(), emptyList()),
            ShallowOcean(6, Vec2D(2, 1), emptyList(), emptyList())
        )
        Sea.tiles.addAll(listOfTiles)
    }

    @Test
    fun testGetTileById() {
        val tile = Sea.getTileById(1)
        assert(tile is Shore)
    }

    @Test
    fun testGetTileByPos() {
        val tile = Sea.getTileByPos(Vec2D(0, 0))
        assert(tile != null)
    }
}
