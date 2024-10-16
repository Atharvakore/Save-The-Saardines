package tiles

import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TileTest {

    private val sea: Sea = Sea()

    @BeforeEach
    fun setUp() {
        val tile62: Tile = Shore(62, Vec2D(1, 6), emptyList(), mutableListOf(), false)
        val tile72: Tile = Shore(72, Vec2D(1, 7), emptyList(), mutableListOf(), false)
        val tile82: Tile = Shore(82, Vec2D(1, 8), emptyList(), mutableListOf(), false)
        val tile83: Tile = Shore(83, Vec2D(2, 8), emptyList(), mutableListOf(), false)
        val tile74: Tile = ShallowOcean(74, Vec2D(3, 7), emptyList(), mutableListOf())
        val tile63: Tile = ShallowOcean(63, Vec2D(2, 6), emptyList(), mutableListOf())
        val tile73: Tile = ShallowOcean(73, Vec2D(2, 7), emptyList(), mutableListOf())
        val tile75: Tile = ShallowOcean(75, Vec2D(4, 7), emptyList(), mutableListOf())

        val adjTile62: List<Tile?> = listOf(tile72, tile63, tile73, null, null, null)
        val adjTile72: List<Tile?> = listOf(tile62, tile82, tile73, null, null, null)
        val adjTile82: List<Tile?> = listOf(tile72, tile83, tile73, null, null, null)
        val adjTile83: List<Tile?> = listOf(tile82, tile74, tile73, null, null, null)
        val adjTile74: List<Tile?> = listOf(tile75, null, tile63, tile73, tile83, null)
        val adjTile63: List<Tile?> = listOf(tile62, tile73, tile74, null, null, null)
        val adjTile73: List<Tile?> = listOf(tile74, tile63, tile62, tile72, tile82, tile83)

        tile62.adjacentTiles = adjTile62
        tile72.adjacentTiles = adjTile72
        tile82.adjacentTiles = adjTile82
        tile83.adjacentTiles = adjTile83
        tile74.adjacentTiles = adjTile74
        tile63.adjacentTiles = adjTile63
        tile73.adjacentTiles = adjTile73

        val chemical: Garbage = Garbage(1, 100, GarbageType.CHEMICALS, emptySet())
        val oil: Garbage = Garbage(2, 600, GarbageType.OIL, emptySet())
        tile82.addGarbage(oil)
        tile63.addGarbage(chemical)

        val tiles: MutableList<Tile> = mutableListOf(tile62, tile72, tile82, tile83, tile74, tile63, tile73, tile75)

        sea.tiles.addAll(tiles)
    }

    @AfterEach
    fun tearDown() {
        sea.tiles.clear()
    }

    /** Testing basic functionalities **/

    @Test
    fun testGetTileInDirection() {
        val currTile: Tile? = sea.getTileById(73)
        val tile: Tile? = currTile?.getTileInDirection(1, Direction.D0)
        val expectedTile: Tile? = sea.getTileById(74)
        assert(tile == expectedTile)
    }

    @Test
    fun testGetTileInDirection2() {
        val currTile: Tile? = sea.getTileById(73)
        val tile: Tile? = currTile?.getTileInDirection(2, Direction.D0)
        val expectedTile: Tile? = sea.getTileById(75)
        assert(tile == expectedTile)
    }

    @Test
    fun testAddGarbage() {
        val garbageToAdd: Garbage = Garbage(1, 500, GarbageType.PLASTIC, emptySet())
        val currTile: Tile? = sea.getTileById(62)
        currTile?.addGarbage(garbageToAdd)
        assertTrue(currTile?.garbage?.contains(garbageToAdd)!!)
    }

    @Test
    fun testGetAmountOfType() {
        val currTile: Tile? = sea.getTileById(63)
        val amount: Int = currTile?.getAmountOfType(GarbageType.CHEMICALS)!!
        assert(amount == 100)
    }

    @Test
    fun testCurrentOilLevel() {
        val currTile: Tile? = sea.getTileById(82)
        val oilLevel: Int = currTile?.currentOilLevel()!!
        assert(oilLevel == 600)
    }
}
