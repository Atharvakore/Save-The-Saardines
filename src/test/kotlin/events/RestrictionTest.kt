package events

import de.unisaarland.cs.se.selab.events.EndRestriction
import de.unisaarland.cs.se.selab.events.Restriction
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestrictionTest {
    lateinit var ship: Ship
    val sea: Sea = Sea
    var restrictionEvent = Restriction(1, 1, sea, DeepOcean(6, Vec2D(3, 6), listOf(), listOf(), null), 2)
    var endRestriction = EndRestriction(2, 5, sea, DeepOcean(6, Vec2D(3, 6), listOf(), listOf(), null), 1)

    @BeforeAll
    fun setUp() {
        ship = Ship(1, 50, 15, 5000, 7, mutableListOf())

        val tile62: Tile = Shore(62, Vec2D(1, 6), listOf(), listOf(), false)
        val tile72: Tile = Shore(72, Vec2D(1, 7), listOf(), listOf(), false)
        val tile82: Tile = Shore(82, Vec2D(1, 8), listOf(), listOf(), false)
        val tile83: Tile = Shore(83, Vec2D(2, 8), listOf(), listOf(), false)
        val tile74: Tile = ShallowOcean(74, Vec2D(3, 7), listOf(), listOf())
        val tile63: Tile = ShallowOcean(63, Vec2D(2, 6), listOf(), listOf())
        val tile73: Tile = ShallowOcean(73, Vec2D(2, 7), listOf(), listOf())

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

        val tiles: MutableList<Tile> = mutableListOf(
            tile62, tile72, tile82, tile83, tile74, tile63, tile73,
            DeepOcean(0, Vec2D(4, 5), listOf(), listOf(), null),
            DeepOcean(1, Vec2D(5, 5), listOf(), listOf(), null),
            DeepOcean(2, Vec2D(5, 4), listOf(), listOf(), null),
            DeepOcean(3, Vec2D(4, 4), listOf(), listOf(), null),
            DeepOcean(4, Vec2D(3, 4), listOf(), listOf(), null),
            DeepOcean(5, Vec2D(3, 5), listOf(), listOf(), null),
            DeepOcean(6, Vec2D(3, 6), listOf(), listOf(), null),
            DeepOcean(7, Vec2D(4, 6), listOf(), listOf(), null),
        )

        sea.tiles = tiles
    }

    @Test
    fun startRestrictionTest() {
        assertTrue(!restrictionEvent.actUponTick(0) && !endRestriction.actUponTick(0))
        assertTrue(restrictionEvent.actUponTick(1))
        assertTrue((sea.getTileByPos(Vec2D(4, 4))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(4, 4))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(5, 5))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(4, 5))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(4, 6))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(3, 4))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(3, 5))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(3, 6))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(3, 7))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(2, 7))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(2, 6))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(2, 8))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(1, 8))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(1, 1))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(1, 6))?.restrictions ?: true) == 1)
    }

    /**
     * Note Here StartRestriction has radius 2 but end one has 1 so end of test we should have tiles with Restriction
     */
    /**
     * Behaviour of Ships is yet to be tested
     */
    @Test
    fun endRestrictionTest() {
        assertTrue(endRestriction.actUponTick(5))
        assertTrue((sea.getTileByPos(Vec2D(3, 5))?.restrictions ?: true) == 0)
        assertTrue((sea.getTileByPos(Vec2D(4, 5))?.restrictions ?: true) == 0)
        assertTrue((sea.getTileByPos(Vec2D(4, 6))?.restrictions ?: true) == 0)
        assertTrue((sea.getTileByPos(Vec2D(3, 7))?.restrictions ?: true) == 0)
        assertTrue((sea.getTileByPos(Vec2D(2, 6))?.restrictions ?: true) == 0)
        assertTrue((sea.getTileByPos(Vec2D(3, 4))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(4, 4))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(5, 5))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(3, 4))?.restrictions ?: true) == 1)
        assertTrue((sea.getTileByPos(Vec2D(2, 7))?.restrictions ?: true) == 1)
    }
}
