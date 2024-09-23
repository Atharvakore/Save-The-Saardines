package de.unisaarland.cs.se.selab

import com.github.erosb.jsonsKema.Schema
import com.github.erosb.jsonsKema.SchemaLoader
import com.github.erosb.jsonsKema.ValidationFailure
import com.github.erosb.jsonsKema.Validator
import de.unisaarland.cs.se.selab.logger.Logger
import de.unisaarland.cs.se.selab.logger.LoggerStatistics
import de.unisaarland.cs.se.selab.parser.Accumulator
import de.unisaarland.cs.se.selab.parser.CorporationJSONParser
import de.unisaarland.cs.se.selab.parser.MapJSONParser
import de.unisaarland.cs.se.selab.parser.ScenarioJSONParser
import de.unisaarland.cs.se.selab.parser.TasksRewardsParser
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.PrintStream

private val logger = KotlinLogging.logger {}

/**
 * this is the main class
 */
fun main(args: Array<String>) {
    var mapFile: String? = null
    var corporationsFile: String? = null
    var scenarioFile: String? = null
    var maxTicks: Int? = null
    var outputFile: String? = null
    var file: PrintStream = System.out
    for (i in args.indices) {
        when (args[i]) {
            "--map" -> mapFile = args[i + 1]
            "--corporations" -> corporationsFile = args[i + 1]
            "--scenario" -> scenarioFile = args[i + 1]
            "--max_ticks" -> maxTicks = args[i + 1].toInt()
            "--out" -> outputFile = args[i + 1]
            "--help" -> continue
        }
    }
    if (outputFile != null) {
        val x = File(outputFile)
        file = PrintStream(x)
    }
    Logger.setOutBuffer(file)
    val acc: Accumulator? = parse(listOf(mapFile, corporationsFile, scenarioFile), maxTicks)
    if (acc != null && maxTicks != null) {
        LoggerStatistics.sea = acc.map
        val sim = Simulation(acc.corporations.values.toList(), acc.events.values.toList(), maxTicks, acc.map)
        sim.start()
    }
    file.close()
}

/** The main function for parsing */
fun parse(files: List<String?>, maxTicks: Int?): Accumulator? {
    val contents = validate(files)
    val accumulator = Accumulator()
    var cond: Boolean = maxTicks == null
    cond = cond || parseMap(files, contents[0], accumulator) == null
    cond = cond || parseCorporations(files, contents[1], accumulator) == null
    cond = cond || parseScenario(files, contents[2], accumulator) == null
    if (!cond) {
        return accumulator
    }
    return null
}

/** Parsing the Map */
fun parseMap(files: List<String?>, content: String?, accumulator: Accumulator): Accumulator? {
    val mapParser = MapJSONParser(accumulator)
    if (content != null && mapParser.parseMap(content)) {
        Logger.logInitializationInfoSuccess(requireNotNull(files[0]))
    } else {
        Logger.logInitializationInfoFail(requireNotNull(files[0]))
        return null
    }
    return accumulator
}

private fun parseCorporations(files: List<String?>, content: String?, accumulator: Accumulator): Accumulator? {
    val corpParser = CorporationJSONParser(accumulator)
    if (content != null && corpParser.parseCorporationsFile(content)) {
        Logger.logInitializationInfoSuccess(requireNotNull(files[1]))
    } else {
        Logger.logInitializationInfoFail(requireNotNull(files[1]))
        return null
    }
    return accumulator
}
private fun parseScenario(files: List<String?>, content: String?, accumulator: Accumulator): Accumulator? {
    val scenarioParser = ScenarioJSONParser(accumulator)
    val taskPars = TasksRewardsParser(accumulator)
    if (content == null) return null
    var validScenario = scenarioParser.parseGarbage(content) && scenarioParser.parseEvents(content)
    validScenario = validScenario && taskPars.parseRewards(content) && taskPars.parseTasks(content)
    return if (validScenario) {
        Logger.logInitializationInfoSuccess(requireNotNull(files[2]))
        accumulator
    } else {
        Logger.logInitializationInfoFail(requireNotNull(files[2]))
        null
    }
}

private fun validate(files: List<String?>): List<String?> {
    val contents: MutableList<String?> = mutableListOf()
    val validatingSchemas: MutableList<String> = mutableListOf("classpath://schema/map.schema")
    validatingSchemas.add("classpath://schema/corporations.schema")
    validatingSchemas.add("classpath://schema/scenario.schema")
    for (i in 0..2) {
        val file: String? = files[i]
        if (file != null) {
            val validatedFile: String? = readFile(validatingSchemas[i], file)
            contents.add(validatedFile)
        } else {
            break
        }
    }
    return contents
}

private fun readFile(validatingSchema: String, file: String): String? {
    val schema: Schema = SchemaLoader.forURL(validatingSchema).load()
    val validator: Validator = Validator.forSchema(schema)
    val objects: String
    var objectFile: File? = null
    var condition = true
    try {
        objectFile = File(file)
    } catch (notFound: FileNotFoundException) {
        logger.error(notFound) { "error" }
        condition = false
        //  Logger.logInitializationInfoFail(file)
    }
    if (condition) {
        try {
            objects = requireNotNull(objectFile?.readText())
            // objects = objectFile?.readText()
            val fail: ValidationFailure? = validator.validate(objects)
            if (fail != null) {
                logger.error { "fail when parsing $file" }
                // Logger.logInitializationInfoFail(file)
            } else {
                return objects
            }
        } catch (notFound: IOException) {
            logger.error(notFound) { "error" }
            // Logger.logInitializationInfoFail(file)
        }
    }
    return null
}
