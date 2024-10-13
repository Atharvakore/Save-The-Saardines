package test.kotlin.parser

import de.unisaarland.cs.se.selab.logger.Logger
import de.unisaarland.cs.se.selab.parser.Accumulator
import de.unisaarland.cs.se.selab.parser.MapJSONParser
import java.io.PrintStream
import kotlin.test.BeforeTest
import kotlin.test.Test

class SmallMapTest {
    private val x: PrintStream = System.out
    val map = "{\n" +
        "  \"tiles\": [\n" +
        "    {\n" +
        "      \"id\": 1,\n" +
        "      \"coordinates\": {\n" +
        "        \"x\": 0,\n" +
        "        \"y\": 0\n" +
        "      },\n" +
        "      \"category\": \"LAND\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": 2,\n" +
        "      \"coordinates\": {\n" +
        "        \"x\": 1,\n" +
        "        \"y\": 0\n" +
        "      },\n" +
        "      \"category\": \"LAND\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": 3,\n" +
        "      \"coordinates\": {\n" +
        "        \"x\": 2,\n" +
        "        \"y\": 1\n" +
        "      },\n" +
        "      \"category\": \"LAND\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": 4,\n" +
        "      \"coordinates\": {\n" +
        "        \"x\": 1,\n" +
        "        \"y\": 2\n" +
        "      },\n" +
        "      \"category\": \"LAND\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": 5,\n" +
        "      \"coordinates\": {\n" +
        "        \"x\": 0,\n" +
        "        \"y\": 2\n" +
        "      },\n" +
        "      \"category\": \"LAND\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": 6,\n" +
        "      \"coordinates\": {\n" +
        "        \"x\": 0,\n" +
        "        \"y\": 1\n" +
        "      },\n" +
        "      \"category\": \"LAND\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": 7,\n" +
        "      \"coordinates\": {\n" +
        "        \"x\": 1,\n" +
        "        \"y\": 1\n" +
        "      },\n" +
        "      \"category\": \"SHORE\",\n" +
        "      \"harbor\": true\n" +
        "    }\n" +
        "  ]\n" +
        "}"

    @BeforeTest
    fun setBuffer() {
        Logger.setOutBuffer(x)
    }

    @Test
    fun testMapWithEverything() {
        val acc = Accumulator()
        val mapParser = MapJSONParser(acc)
        assert(mapParser.parseMap(map))
    }
}
