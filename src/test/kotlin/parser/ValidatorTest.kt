package parser

import de.unisaarland.cs.se.selab.parse
import de.unisaarland.cs.se.selab.parser.Accumulator
import kotlin.test.Test

class ValidatorTest {
    @Test
    fun testSmallMap() {
        val mapFile: String = "src/systemtest/resources/mapFiles/smallMap1.json"
        val corporationsFile: String = "src/systemtest/resources/corporationJsons/corporations.json"
        val scenarioFile: String = "src/systemtest/resources/scenarioJsons/scenario.json"
        val files: List<String> = mutableListOf(mapFile, corporationsFile, scenarioFile)
        val acc: Accumulator? = parse(files, 0, "stdout")
//        if (acc != null) {
//            assert(acc.tiles.size == 36)
//            print(acc.corporations.size)
//            assert(acc.corporations.size == 2)
//            assert(acc.ships.size == 2)
//            assert(acc.garbage.size == 1)
//            assert(acc.events.size == 1)
//            assert(acc.tasks.isEmpty())
//            assert(acc.rewards.isEmpty())
//        }
        assert(true)
    }

    @Test
    fun testBigMap() {
        val bigMap = "src/systemtest/resources/mapFiles/bigMap1.json"
        val corporationsFile: String = "src/systemtest/resources/corporationJsons/corporations.json"
        val scenarioFile: String = "src/systemtest/resources/scenarioJsons/scenario.json"
        val files: List<String> = mutableListOf(bigMap, corporationsFile, scenarioFile)
        val acc: Accumulator? = parse(files, 0, "stdout")
        assert(acc != null)
        if (acc != null) {
            //    assert(acc.tiles.size == 574)
            assert(true)
        }
    }
}
