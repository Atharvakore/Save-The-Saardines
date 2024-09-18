package ship

import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.tiles.GarbageType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContainerTest {

    private val container: Container = Container(GarbageType.CHEMICALS, 1000)

    /** Testing basic functionalities **/

    @Test
    fun testGetGarbageCapacity() {
        assert(container.getGarbageCapacity() == 1000)
    }

    @Test
    fun giveGarbage() {
        container.giveGarbage()
        assert(container.garbageLoad == 0)
    }

    @Test
    fun testCollect() {
        assert(container.collect(500, GarbageType.CHEMICALS) == 500)
        assert(container.garbageLoad == 500)
    }
}
