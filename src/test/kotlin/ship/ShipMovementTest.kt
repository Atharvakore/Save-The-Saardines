package ship

import corporation.UTFactory
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShipMovementTest {
    var sea = Sea()
    private val factory = UTFactory()

    @BeforeEach
    fun setUp() {
        factory.createTestingMap()
        sea = factory.sea
    }

    /** Testing basic functionalities **/

    @Test
    fun testMove() {
        val container = Container(GarbageType.PLASTIC, 2000)
        val scouting = ScoutingShip(2)
        val capabilities = mutableListOf(CollectingShip(mutableListOf(container)), scouting)
        val collectingShip = Ship(1, 50, 10, 3000, 9, capabilities)

        val tile58 = sea.getTileByPos(Vec2D(7, 5))!!
        val tile48 = sea.getTileByPos(Vec2D(7, 4))!!
        tile48.addGarbage(Garbage(1, 2000, GarbageType.PLASTIC, emptySet()))
        collectingShip.position = tile58

        val fov = scouting.getTilesWithGarbageInFoV(sea, tile58)
        assertTrue(fov.contains(tile48))
    }
}
