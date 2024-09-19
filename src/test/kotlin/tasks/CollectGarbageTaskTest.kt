package tasks

import corporation.UTFactory
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.CollectGarbageTask
import de.unisaarland.cs.se.selab.tasks.ContainerReward
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CollectGarbageTaskTest {

    private val factory = UTFactory()

    @BeforeEach
    fun setUp() {
        factory.createTestingMap()
        val initialTile = Sea.getTileByPos(Vec2D(5, 7)) ?: error("Tile not found at position (5,7)")
        val targetTile = Sea.getTileByPos(Vec2D(6, 6)) ?: error("Tile not found at position (6,6)")
        val container = Container(GarbageType.CHEMICALS, 5000)
        val collectingCapability = CollectingShip(mutableListOf(container))
        val collectingReward = ContainerReward(1, collectingCapability, container)
        val ship1 = Ship(1, 50, 15, 5000, 7, mutableListOf())
        var ship2 = Ship(2, 50, 15, 5000, 7, mutableListOf())
        ship1.position = initialTile
        val task = CollectGarbageTask(
            5,
            1,
            ship1,
            collectingReward,
            ship2,
            targetTile
        )
    }

    @Test
    fun checkConditionTest(){
        assertTrue{ship1.}
    }

    //Testing cannot be done further as move ships is yet not complete
}

