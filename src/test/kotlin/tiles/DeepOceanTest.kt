package tiles

import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Vec2D
import kotlin.test.Test

class DeepOceanTest {

    @Test
    fun testGetCurrent() {
        val deepOcean = DeepOcean(1, Vec2D(0, 0), listOf(), listOf(), Current(1, Direction.D60, 1))
        assert(deepOcean.getCurrent() == Current(1, Direction.D60, 1))
    }
}
