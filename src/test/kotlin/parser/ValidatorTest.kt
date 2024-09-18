package parser

import de.unisaarland.cs.se.selab.parse
import de.unisaarland.cs.se.selab.parser.Accumulator
import kotlin.test.Test

class ValidatorTest {
    @Test
    fun test() {
        val mapFile: String = "src/systemtest/resources/mapFiles/smallMap1.json"
        val corporationsFile: String = "src/systemtest/resources/corporationJsons/corporations.json"
        val scenarioFile: String = "src/systemtest/resources/scenarioJsons/scenario.json"
        val files: List<String> = mutableListOf(mapFile, corporationsFile, scenarioFile)
        val acc: Accumulator? = parse(files, 0, "stdout")
        assert(acc != null)
        /*if (acc != null) {
            assert(acc.tiles.size == 36)
            assert(acc.corporations.size == 2)
            assert(acc.ships.size == 2)*/
    }
}
