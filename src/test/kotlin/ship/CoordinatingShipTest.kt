package ship

import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoordinatingShipTest {

    private lateinit var coordinatingCapability: CoordinatingShip
    private lateinit var coordinatingShip: Ship
    private val sea: Sea = Sea

    @BeforeEach
    fun setUp() {
        coordinatingCapability = CoordinatingShip(1)
        coordinatingShip = Ship(1, 50, 15, 5000, 7, mutableListOf(coordinatingCapability))

        val tile62: Tile = Shore(62, Vec2D(1, 6), emptyList(), emptyList(), false)
        val tile72: Tile = Shore(72, Vec2D(1, 7), emptyList(), emptyList(), false)
        val tile82: Tile = Shore(82, Vec2D(1, 8), emptyList(), emptyList(), false)
        val tile83: Tile = Shore(83, Vec2D(2, 8), emptyList(), emptyList(), false)
        val tile74: Tile = ShallowOcean(74, Vec2D(3, 7), emptyList(), emptyList())
        val tile63: Tile = ShallowOcean(63, Vec2D(2, 6), emptyList(), emptyList())
        val tile73: Tile = ShallowOcean(73, Vec2D(2, 7), emptyList(), emptyList())

        val adjTile62: List<Tile?> = listOf(tile72, tile63, tile73, null, null, null)

        val adjTile72: List<Tile?> = listOf(tile62, tile82, tile73, null, null, null)

        val adjTile82: List<Tile?> = listOf(tile72, tile83, tile73, null, null, null)

        val adjTile83: List<Tile?> = listOf(tile82, tile74, tile73, null, null, null)

        val adjTile74: List<Tile?> = listOf(tile63, tile73, tile83, null, null, null)

        val adjTile63: List<Tile?> = listOf(tile62, tile73, tile74, null, null, null)

        val adjTile73: List<Tile?> = listOf(tile62, tile72, tile82, tile83, tile74, tile63)

        tile62.adjacentTiles = adjTile62
        tile72.adjacentTiles = adjTile72
        tile82.adjacentTiles = adjTile82
        tile83.adjacentTiles = adjTile83
        tile74.adjacentTiles = adjTile74
        tile63.adjacentTiles = adjTile63
        tile73.adjacentTiles = adjTile73

        val garbage: Garbage = Garbage(1, 500, GarbageType.CHEMICALS, null)
        tile63.addGarbage(garbage)

        val tiles: MutableList<Tile> = mutableListOf(tile62, tile72, tile82, tile83, tile74, tile63, tile73)

        sea.tiles.addAll(tiles)
    }

    /** Testing basic functionalities **/

    @Test
    fun testGetTilesWithGarbageInFoV() {
        val tilesWithGarbage: List<Tile> = coordinatingCapability.getTilesWithGarbageInFoV(sea, sea.getTileById(73)!!)
        assert(tilesWithGarbage.size == 1)
        assert(tilesWithGarbage.contains(sea.getTileById(63)!!))
    }
}
