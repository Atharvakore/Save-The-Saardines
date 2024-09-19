package tasks

import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.CollectGarbageTask
import de.unisaarland.cs.se.selab.tasks.ContainerReward
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.tiles.GarbageType
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CollectGarbageTaskTest {
    val oilContainer: Container = Container(GarbageType.OIL, 500)
    var containerReward = ContainerReward(1, ,)
    var ship1 = Ship(1, 50, 15, 5000, 7, mutableListOf())
    var ship2 = Ship(2, 50, 15, 5000, 7, mutableListOf())
    var task = CollectGarbageTask(2,1,ship1, oilContainer ,ship2,)
}
