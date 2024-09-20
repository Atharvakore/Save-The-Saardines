package tiles

import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class GarbageTest {

    private val seaInstance: Sea = Sea

    @BeforeEach
    fun setUp() {
        val tile1: Shore = Shore(1, Vec2D(0, 0), emptyList(), emptyList(), false)
        val tile2: Shore = Shore(2, Vec2D(0, 1), emptyList(), emptyList(), false)
        val tile3: DeepOcean = DeepOcean(3, Vec2D(1, 0), emptyList(), emptyList(), Current(10, Direction.D0, 1))
        val tile4: DeepOcean = DeepOcean(4, Vec2D(1, 1), emptyList(), emptyList(), Current(10, Direction.D60, 1))
        val tile5: ShallowOcean = ShallowOcean(5, Vec2D(2, 0), emptyList(), emptyList())
        val tile6: ShallowOcean = ShallowOcean(6, Vec2D(2, 1), emptyList(), emptyList())

        tile3.adjacentTiles = listOf(tile5)
        val listOfTiles: MutableList<Tile> = mutableListOf(tile1, tile2, tile3, tile4, tile5, tile6)
        Sea.tiles.addAll(listOfTiles)
    }

    @Test
    fun testCreateGarbage() {
        val garbage = Garbage.createGarbage(10, GarbageType.OIL)
        assert(garbage.amount == 10)
        assert(garbage.type == GarbageType.OIL)
    }

    @Test
    fun testDrift() {
        val currentTile: Tile? = seaInstance.getTileById(3)
        val garbage: Garbage = Garbage.createGarbage(10, GarbageType.OIL)
        currentTile?.addGarbage(garbage)
        garbage.drift(currentTile as DeepOcean)
        val garbageInAdjacentTile: List<Garbage>? = seaInstance.getTileById(5)?.garbage
        assertTrue(garbageInAdjacentTile?.contains(garbage)!!)
    }
}
