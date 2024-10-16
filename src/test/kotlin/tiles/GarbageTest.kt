package tiles

import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.MaxGarbageId.createGarbage
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class GarbageTest {

    private lateinit var seaInstance: Sea

    @BeforeEach
    fun setUp() {
        val tile1: Shore = Shore(1, Vec2D(0, 0), emptyList(), mutableListOf(), false)
        val tile2: Shore = Shore(2, Vec2D(0, 1), emptyList(), mutableListOf(), false)
        val tile3: DeepOcean = DeepOcean(3, Vec2D(1, 0), emptyList(), mutableListOf(), Current(10, Direction.D0, 1))
        val tile4: DeepOcean = DeepOcean(4, Vec2D(1, 1), emptyList(), mutableListOf(), Current(10, Direction.D0, 1))
        val tile5: ShallowOcean = ShallowOcean(5, Vec2D(2, 0), emptyList(), mutableListOf())
        val tile6: ShallowOcean = ShallowOcean(6, Vec2D(2, 1), emptyList(), mutableListOf())

        tile3.adjacentTiles = listOf(tile5)
        tile4.adjacentTiles = listOf(null)

        val listOfTiles: MutableList<Tile> = mutableListOf(tile1, tile2, tile3, tile4, tile5, tile6)
        seaInstance = Sea()
        seaInstance.tiles.addAll(listOfTiles)
    }

    @Test
    fun testCreateGarbage() {
        val garbage = createGarbage(10, GarbageType.OIL)
        assert(garbage.amount == 10)
        assert(garbage.type == GarbageType.OIL)
    }
    /*
       @Test
       fun testDrift() {
           val currentTile: Tile? = Sea.getTileById(3)
           val garbage: Garbage = Garbage.createGarbage(10, GarbageType.OIL)
           currentTile?.addGarbage(garbage)
           garbage.drift((currentTile ?: error("Shouldn't be null")) as DeepOcean)
           val garbageInAdjacentTile: List<Garbage>? = seaInstance.getTileById(5)?.garbage
           assertTrue(garbageInAdjacentTile?.contains(garbage)!!)
       }*/

    @Test
    fun garbageDriftToNull() {
        val currentTile: DeepOcean = (seaInstance.getTileById(4) ?: error("null assertion message")) as DeepOcean
        val garbage: Garbage = createGarbage(10, GarbageType.OIL)
        currentTile.addGarbage(garbage)
        garbage.drift(
            currentTile,
            currentTile.getTileInDirection(1, Direction.D0)!!,
            currentTile.getCurrent()!!
        )
    }
}
