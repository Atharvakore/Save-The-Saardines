package ship

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class CollectingShipTest {

    private lateinit var collectingCapability: CollectingShip
    private lateinit var collectingShip: Ship
    private val sea: Sea = Sea()

    @BeforeEach
    fun setUp() {
        val chemicalContainer: Container = Container(GarbageType.CHEMICALS, 500)
        val oilContainer: Container = Container(GarbageType.OIL, 500)
        val plasticContainer: Container = Container(GarbageType.PLASTIC, 500)

        chemicalContainer.garbageLoad = 400
        oilContainer.garbageLoad = 500
        plasticContainer.garbageLoad = 300

        val auxiliaryContainers: MutableList<Container> = mutableListOf(
            chemicalContainer,
            oilContainer,
            plasticContainer
        )

        collectingCapability = CollectingShip(auxiliaryContainers)
        collectingShip = Ship(1, 50, 15, 5000, 7, mutableListOf(collectingCapability))
        val c1 = Corporation(1, "c1", mutableListOf(collectingShip), emptyList(), emptyList(), mutableListOf())
        collectingShip.owner = c1

        val tile62: Tile = Shore(62, Vec2D(1, 6), emptyList(), mutableListOf(), false)
        val tile72: Tile = Shore(72, Vec2D(1, 7), emptyList(), mutableListOf(), false)
        val tile82: Tile = Shore(82, Vec2D(1, 8), emptyList(), mutableListOf(), false)
        val tile83: Tile = Shore(83, Vec2D(2, 8), emptyList(), mutableListOf(), false)
        val tile74: Tile = ShallowOcean(74, Vec2D(3, 7), emptyList(), mutableListOf())
        val tile63: Tile = ShallowOcean(63, Vec2D(2, 6), emptyList(), mutableListOf())
        val tile73: Tile = ShallowOcean(73, Vec2D(2, 7), emptyList(), mutableListOf())

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

        val garbage: Garbage = Garbage(1, 100, GarbageType.CHEMICALS, emptySet())
        tile63.addGarbage(garbage)

        val tiles: MutableList<Tile> = mutableListOf(tile62, tile72, tile82, tile83, tile74, tile63, tile73)

        collectingShip.position = tile63

        sea.tiles.addAll(tiles)
    }

    @AfterEach
    fun tearDown() {
        sea.tiles.clear()
    }
}
