package tasks

import corporation.UTFactory
import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.CollectGarbageTask
import de.unisaarland.cs.se.selab.tasks.ContainerReward
import de.unisaarland.cs.se.selab.tasks.Reward
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import test.kotlin.Witchcraft
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RewardsTest {

    var ship2: Ship = Ship(2, 50, 10, 100, 20, mutableListOf())
    lateinit var targetTile: Tile
    lateinit var initialTile: Tile
    lateinit var task: Task
    lateinit var collectingReward: Reward
    var container: Container = Container(
        GarbageType.CHEMICALS,
        1000
    )
    var collectingCapability: CollectingShip = CollectingShip(
        mutableListOf(container),
    )
    var ship1 = Ship(1, 50, 10, 1000, 15, mutableListOf(collectingCapability))
    val corporation = Corporation(
        5,
        "sa",
        mutableListOf(ship1, ship2),
        mutableListOf(),
        mutableListOf(GarbageType.CHEMICALS),
        mutableListOf()
    )
    private val factory = UTFactory()

    @BeforeEach
    fun setUp() {
        factory.createTestingMap()
        val initialTile = Sea.getTileByPos(Vec2D(5, 7)) ?: error("Tile not found at position (5,7)")
        targetTile = Sea.getTileByPos(Vec2D(6, 6)) ?: error("Tile not found at position (6,6)")
        container = Container(GarbageType.CHEMICALS, 5000)
        collectingCapability = CollectingShip(mutableListOf(container))
        collectingReward = ContainerReward(1, collectingCapability, container)
        ship1 = Ship(1, 50, 15, 5000, 7, mutableListOf())
        ship2 = Ship(2, 50, 15, 5000, 7, mutableListOf(collectingCapability))
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
    fun checkApplyRewardFalse() {
        assertFalse(ship1.capabilities.contains(collectingCapability))
    }

    @Test
    fun checkApplyRewardForShipWithEmptyCapabilitiesTrue() {
        collectingReward.applyReward(ship1)
        assertTrue(ship1.capabilities.contains(collectingCapability))
    }

    @Test
    fun checkApplyRewardForShipWithCapabilitiesTrue() {
        collectingReward.applyReward(ship2)
        assertTrue(ship1.capabilities.contains(collectingCapability))
    }
}
