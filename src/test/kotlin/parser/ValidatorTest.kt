package parser

import de.unisaarland.cs.se.selab.logger.Logger
import de.unisaarland.cs.se.selab.parse
import de.unisaarland.cs.se.selab.parser.Accumulator
import java.io.PrintStream
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class ValidatorTest {
    private val x: PrintStream = System.out

    @BeforeTest
    fun setBuffer() {
        Logger.setOutBuffer(x)
    }

    @Test
    fun testSmallMap() {
        val mapFile: String = "src/systemtest/resources/mapFiles/smallMap1.json"
        val corporationsFile: String = "src/systemtest/resources/corporationJsons/corporations.json"
        val scenarioFile: String = "src/systemtest/resources/scenarioJsons/scenario.json"
        val files: List<String> = mutableListOf(mapFile, corporationsFile, scenarioFile)
        val acc: Accumulator? = parse(files, 0)
        if (acc != null) {
            assert(acc.tiles.size == 36)
            assert(acc.corporations.size == 2)
            assert(acc.ships.size == 2)
            assert(acc.garbage.size == 1)
            assert(acc.events.size == 2)
            assert(acc.tasks.isEmpty())
            assert(acc.rewards.isEmpty())
        }
    }

    @Test
    fun testInvalidMap() {
        val mapFile: String = "src/systemtest/resources/mapFiles/invalidMap.json"
        val corporationsFile: String = "src/systemtest/resources/corporationJsons/corporations.json"
        val scenarioFile: String = "src/systemtest/resources/scenarioJsons/scenario.json"
        val files: List<String> = mutableListOf(mapFile, corporationsFile, scenarioFile)
        val accumulator: Accumulator? = parse(files, 0)
        assert(accumulator == null)
    }

    @Test
    fun testBigMap() {
        val bigMap = "src/systemtest/resources/mapFiles/bigMap1.json"
        val corporationsFile: String = "src/systemtest/resources/corporationJsons/corporations.json"
        val scenarioFile: String = "src/systemtest/resources/scenarioJsons/scenario.json"
        val files: List<String> = mutableListOf(bigMap, corporationsFile, scenarioFile)
        val accumulator = parse(files, 100)
        assert(accumulator == null)
    }

    @AfterTest
    fun closeBuffer() {
        x.close()
    }
}
