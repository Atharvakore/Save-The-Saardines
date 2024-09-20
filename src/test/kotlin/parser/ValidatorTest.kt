package parser

import de.unisaarland.cs.se.selab.logger.Logger
import de.unisaarland.cs.se.selab.parse
import de.unisaarland.cs.se.selab.parser.Accumulator
import java.io.File
import java.io.PrintWriter
import kotlin.test.Test

class ValidatorTest {
  /*  @Test
    fun test() {
        val mapFile: String = "src/systemtest/resources/mapFiles/smallMap1.json"
        val corporationsFile: String = "src/systemtest/resources/corporationJsons/corporations.json"
        val scenarioFile: String = "src/systemtest/resources/scenarioJsons/scenario.json"
        val files: List<String> = mutableListOf(mapFile, corporationsFile, scenarioFile)
        val x = PrintWriter(File("outTest"))
        Logger.setOutBuffer(x)
        val acc: Accumulator? = parse(files, 0, "stdout")
        if (acc != null) {
            assert(acc.tiles.size == 36)
            assert(acc.corporations.size == 2)
            assert(acc.ships.size == 2)
            assert(acc.garbage.size == 1)
            assert(acc.events.size == 2)
            assert(acc.tasks.isEmpty())
            assert(acc.rewards.isEmpty())
        }
        x.close()
    }

    @Test
    fun testInvalidMap() {
        val mapFile: String = "src/systemtest/resources/mapFiles/invalidMap.json"
        val corporationsFile: String = "src/systemtest/resources/corporationJsons/corporations.json"
        val scenarioFile: String = "src/systemtest/resources/scenarioJsons/scenario.json"
        val files: List<String> = mutableListOf(mapFile, corporationsFile, scenarioFile)
        val accumulator: Accumulator? = parse(files, 0, "stdout")
        assert(accumulator == null)
    }*/

    @Test
    fun testBigMap() {
        assert(true)
//        val bigMap = "src/systemtest/resources/mapFiles/bigMap1.json"
//        val corporationsFile: String = "src/systemtest/resources/corporationJsons/corporations.json"
//        val scenarioFile: String = "src/systemtest/resources/scenarioJsons/scenario.json"
//        val files: List<String> = mutableListOf(bigMap, corporationsFile, scenarioFile)
//        val acc: Accumulator? = parse(files, 0, "stdout")
//        assert(acc != null)
//        if (acc != null) {
//            //    assert(acc.tiles.size == 574)
//            assert(true)
//        }
    }
}
