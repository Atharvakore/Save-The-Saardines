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
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GarbageTest {

    private val seaInstance: Sea = Sea

    @BeforeEach
    fun setUp() {
        val listOfTiles: MutableList<Tile> = mutableListOf(
            Shore(1, Vec2D(0, 0), listOf(), listOf(), false),
            Shore(2, Vec2D(0, 1), listOf(), listOf(), false),
            DeepOcean(3, Vec2D(1, 0), listOf(), listOf(), Current(1, Direction.D0, 1)),
            DeepOcean(4, Vec2D(1, 1), listOf(), listOf(), Current(1, Direction.D60, 1)),
            ShallowOcean(5, Vec2D(2, 0), listOf(), listOf()),
            ShallowOcean(6, Vec2D(2, 1), listOf(), listOf())
        )
        Sea.tiles = listOfTiles
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
        val garbage = Garbage.createGarbage(10, GarbageType.OIL)
        currentTile?.addGarbage(garbage)
        garbage.drift(currentTile as DeepOcean)
        val garbageInAdjacentTile: List<Garbage>? = seaInstance.getTileById(5)?.garbage
        assertTrue(garbageInAdjacentTile?.contains(garbage)!!)
    }
}
