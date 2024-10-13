package tasks

import corporation.UTFactory
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.CollectGarbageTask
import de.unisaarland.cs.se.selab.tasks.ContainerReward
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import test.kotlin.Witchcraft

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CollectGarbageTaskTest {
    lateinit var ship1: Ship
    lateinit var ship2: Ship
    lateinit var targetTile: Tile
    lateinit var initialTile: Tile
    lateinit var task: Task
    private val factory = UTFactory()
    var sea = Sea()

    @BeforeEach
    fun setUp() {
        factory.createTestingMap()
        sea = factory.sea
        val initialTile = sea.getTileByPos(Vec2D(5, 7)) ?: error("Tile not found at position (5,7)")
        targetTile = sea.getTileByPos(Vec2D(6, 6)) ?: error("Tile not found at position (6,6)")
        val container = Container(GarbageType.CHEMICALS, 5000)
        val collectingCapability = CollectingShip(mutableListOf(container))
        val collectingReward = ContainerReward(1, collectingCapability, container)
        ship1 = Ship(1, 50, 15, 5000, 7, mutableListOf())
        ship2 = Ship(2, 50, 15, 5000, 7, mutableListOf())
        ship1.position = initialTile
        task = CollectGarbageTask(
            5,
            1,
            ship1,
            collectingReward,
            ship2,
            targetTile
        )
        Witchcraft.swallowObject(task)
    }

    @Test
    fun checkConditionTestFalse() {
        assertTrue { ship1.position != targetTile }
    }

    @Test
    fun checkConditionTestTrue() {
        ship1.position = targetTile
        assertTrue { ship1.position == targetTile }
    }

    @Test
    fun checkTargetTileTrue() {
        assertTrue { task.getGoal() == targetTile }
    }
    // actUponTick Testing cannot be done here needs to be done in integration testing
}
