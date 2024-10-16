package systemtests

import de.unisaarland.cs.se.selab.tasks.Task
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
import kotlin.collections.Map

class Map {
    val sea = Sea()
    fun createShallowOceanTiles(
        numOfTiles: Int,
        pos: Map<Int, Vec2D>,
        adjacentTiles: Map<Int, List<Tile>>,
    ): List<ShallowOcean> {
        return (1 until numOfTiles).map { id ->
            val position = pos[id] ?: Vec2D(0, 0)
            val adjacent = adjacentTiles[id].orEmpty()
            val tileGarbage = mutableListOf<Garbage>()
            ShallowOcean(
                id,
                position,
                adjacent,
                tileGarbage,
            )
        }
    }

    fun createGarbage(
        numOfPiles: Int,
        amount: Map<Int, Int>,
        type: Map<Int, GarbageType>,
    ): List<Garbage> {
        return (1 until numOfPiles).map { id ->
            val garbageAmount = amount[id] ?: 0
            val garbageType = type[id] ?: GarbageType.OIL
            Garbage(id, garbageAmount, garbageType, emptySet())
        }
    }

    fun createEmptyTasks(num: Int): Map<Int, List<Task>> {
        return (1 until num).associateWith { emptyList() }
    }

    /**
     * creation of the map provided in the spec
     * not considering adjacentTiles
     * */
    fun createTestingMap() {
        val shoreTiles = createShoreForSea()
        val shallowOceanTiles = listOf(
            ShallowOcean(23, Vec2D(2, 2), emptyList(), mutableListOf()),
            ShallowOcean(24, Vec2D(3, 2), emptyList(), mutableListOf()),
            ShallowOcean(25, Vec2D(4, 2), emptyList(), mutableListOf()),
            ShallowOcean(26, Vec2D(5, 2), emptyList(), mutableListOf()),
            ShallowOcean(27, Vec2D(6, 2), emptyList(), mutableListOf()),
            ShallowOcean(28, Vec2D(7, 2), emptyList(), mutableListOf()),
            ShallowOcean(33, Vec2D(2, 3), emptyList(), mutableListOf()),
            ShallowOcean(38, Vec2D(7, 3), emptyList(), mutableListOf()),
            ShallowOcean(43, Vec2D(2, 4), emptyList(), mutableListOf()),
            ShallowOcean(48, Vec2D(7, 4), emptyList(), mutableListOf()),
            ShallowOcean(53, Vec2D(2, 5), emptyList(), mutableListOf()),
            ShallowOcean(58, Vec2D(7, 5), emptyList(), mutableListOf()),
            ShallowOcean(63, Vec2D(2, 6), emptyList(), mutableListOf()),
            ShallowOcean(68, Vec2D(7, 6), emptyList(), mutableListOf()),
            ShallowOcean(73, Vec2D(2, 7), emptyList(), mutableListOf()),
            ShallowOcean(74, Vec2D(3, 7), emptyList(), mutableListOf()),
            ShallowOcean(75, Vec2D(4, 7), emptyList(), mutableListOf()),
            ShallowOcean(76, Vec2D(5, 7), emptyList(), mutableListOf()),
            ShallowOcean(77, Vec2D(6, 7), emptyList(), mutableListOf()),
            ShallowOcean(78, Vec2D(7, 7), emptyList(), mutableListOf())
        )

        val deepOceanTiles = listOf(
            DeepOcean(33, Vec2D(3, 3), emptyList(), mutableListOf(), current = Current(10, Direction.D60, 1)),
            DeepOcean(34, Vec2D(3, 4), emptyList(), mutableListOf(), current = Current(10, Direction.D180, 1)),
            DeepOcean(35, Vec2D(3, 5), emptyList(), mutableListOf(), current = Current(10, Direction.D60, 1)),
            DeepOcean(36, Vec2D(3, 6), emptyList(), mutableListOf(), current = Current(10, Direction.D0, 1)),
            DeepOcean(43, Vec2D(4, 3), emptyList(), mutableListOf(), current = Current(10, Direction.D180, 1)),
            DeepOcean(44, Vec2D(4, 6), emptyList(), mutableListOf(), current = Current(10, Direction.D0, 1)),
            DeepOcean(53, Vec2D(5, 3), emptyList(), mutableListOf(), current = Current(10, Direction.D180, 1)),
            DeepOcean(56, Vec2D(6, 3), emptyList(), mutableListOf(), current = Current(10, Direction.D180, 1)),
            DeepOcean(56, Vec2D(6, 4), emptyList(), mutableListOf(), current = Current(10, Direction.D240, 1)),
            DeepOcean(56, Vec2D(6, 5), emptyList(), mutableListOf(), current = Current(10, Direction.D300, 1)),
            DeepOcean(56, Vec2D(6, 6), emptyList(), mutableListOf(), current = Current(10, Direction.D0, 1)),
            DeepOcean(66, Vec2D(5, 6), emptyList(), mutableListOf(), current = Current(10, Direction.D0, 1))
        )

        sea.tiles.addAll(shoreTiles)
        sea.tiles.addAll(shallowOceanTiles)
        sea.tiles.addAll(deepOceanTiles)

        for (tile in sea.tiles) {
            tile.adjacentTiles = findAdjacentTiles(tile.pos)
        }
        sea.tileIndex = sea.tiles.associateBy { it.pos }
    }

    private fun createShoreForSea(): List<Shore> {
        val shoreTiles = listOf(
            Shore(12, Vec2D(1, 1), emptyList(), mutableListOf(), harbor = false),
            Shore(13, Vec2D(2, 1), emptyList(), mutableListOf(), harbor = false),
            Shore(14, Vec2D(3, 1), emptyList(), mutableListOf(), harbor = true),
            Shore(15, Vec2D(4, 1), emptyList(), mutableListOf(), harbor = false),
            Shore(16, Vec2D(5, 1), emptyList(), mutableListOf(), harbor = false),
            Shore(17, Vec2D(6, 1), emptyList(), mutableListOf(), harbor = false),
            Shore(18, Vec2D(7, 1), emptyList(), mutableListOf(), harbor = false),
            Shore(19, Vec2D(8, 1), emptyList(), mutableListOf(), harbor = false),
            Shore(22, Vec2D(1, 2), emptyList(), mutableListOf(), harbor = false),
            Shore(29, Vec2D(8, 2), emptyList(), mutableListOf(), harbor = false),
            Shore(32, Vec2D(1, 3), emptyList(), mutableListOf(), harbor = false),
            Shore(39, Vec2D(8, 3), emptyList(), mutableListOf(), harbor = false),
            Shore(42, Vec2D(1, 4), emptyList(), mutableListOf(), harbor = false),
            Shore(49, Vec2D(8, 4), emptyList(), mutableListOf(), harbor = false),
            Shore(52, Vec2D(1, 5), emptyList(), mutableListOf(), harbor = true),
            Shore(59, Vec2D(8, 5), emptyList(), mutableListOf(), harbor = true),
            Shore(62, Vec2D(1, 6), emptyList(), mutableListOf(), harbor = false),
            Shore(69, Vec2D(8, 6), emptyList(), mutableListOf(), harbor = false),
            Shore(72, Vec2D(1, 7), emptyList(), mutableListOf(), harbor = false),
            Shore(79, Vec2D(8, 7), emptyList(), mutableListOf(), harbor = false),
            Shore(82, Vec2D(1, 8), emptyList(), mutableListOf(), harbor = false),
            Shore(89, Vec2D(8, 8), emptyList(), mutableListOf(), harbor = false),
            Shore(83, Vec2D(2, 8), emptyList(), mutableListOf(), harbor = false),
            Shore(84, Vec2D(3, 8), emptyList(), mutableListOf(), harbor = false),
            Shore(85, Vec2D(4, 8), emptyList(), mutableListOf(), harbor = false),
            Shore(86, Vec2D(5, 8), emptyList(), mutableListOf(), harbor = false),
            Shore(87, Vec2D(6, 8), emptyList(), mutableListOf(), harbor = false),
            Shore(88, Vec2D(7, 8), emptyList(), mutableListOf(), harbor = false),
        )
        return shoreTiles
    }

    private fun findAdjacentTiles(pos: Vec2D): List<Tile?> {
        val nextValue = if (pos.posX % 2 == 0 && pos.posY % 2 == 0) {
            1
        } else {
            0
        }

        val list = listOf(
            sea.getTileByPos(Vec2D(pos.posX + 1, pos.posY)), // East
            sea.getTileByPos(Vec2D(pos.posX - 1, pos.posY)), // West
            sea.getTileByPos(Vec2D(pos.posX + nextValue, pos.posY + 1)), // Southeast
            sea.getTileByPos(Vec2D(pos.posX + nextValue - 1, pos.posY - 1)), // Northwest
            sea.getTileByPos(Vec2D(pos.posX + nextValue, pos.posY - 1)), // Northeast
            sea.getTileByPos(Vec2D(pos.posX + nextValue - 1, pos.posY + 1)) // Southwest
        )
        return list
    }
}
