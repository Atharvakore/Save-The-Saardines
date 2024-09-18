package de.unisaarland.cs.se.selab

import com.github.erosb.jsonsKema.Schema
import com.github.erosb.jsonsKema.SchemaLoader
import com.github.erosb.jsonsKema.ValidationFailure
import com.github.erosb.jsonsKema.Validator
import de.unisaarland.cs.se.selab.logger.Logger
import de.unisaarland.cs.se.selab.parser.Accumulator
import de.unisaarland.cs.se.selab.parser.CorporationJSONParser
import de.unisaarland.cs.se.selab.parser.MapJSONParser
import de.unisaarland.cs.se.selab.parser.ScenarioJSONParser
import de.unisaarland.cs.se.selab.parser.TasksRewardsParser
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

private val logger = KotlinLogging.logger {}

/**
 * this is the main class
 */
fun main(args: Array<String>) {
    var mapFile: String? = null
    var corporationsFile: String? = null
    var scenarioFile: String? = null
    var maxTicks: Int? = null
    var outputFile: String? = "stdout"
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
    val acc: Accumulator? = parse(listOf(mapFile, corporationsFile, scenarioFile), maxTicks, outputFile)
    if (maxTicks != null && acc != null && acc.map != null) {
        val sim = Simulation(acc.corporations.values.toList(), acc.events.values.toList(), maxTicks, acc.map!!)
        sim.start()
    }
}

/** The main function for parsing */
fun parse(files: List<String?>, maxTicks: Int?, outputFile: String?): Accumulator? {
    val contents = validate(files)
    val accumulator = Accumulator()
    if (contents != null) {
        var cond: Boolean = maxTicks == null || outputFile == null
        cond = cond || parseMap(files, contents, accumulator) == null
        if (cond || parseScenario(files, contents, accumulator) == null) {
            return null
        }
    }
    return if (contents != null) {
        accumulator
    } else {
        null
    }
}

/** Parsing the Map */
fun parseMap(files: List<String?>, contents: List<String>, accumulator: Accumulator): Accumulator? {
    var condition = true
    val mapParser = MapJSONParser(accumulator)
    if (files[0] != null && mapParser.parseMap(contents[0])) {
        files[0]?.let { Logger.logInitializationInfoSuccess(it) }
    } else {
        condition = false
    }
    val corpParser = CorporationJSONParser(accumulator)
    if (corpParser.parseCorporationsFile(contents[1])) {
        files[1]?.let { Logger.logInitializationInfoSuccess(it) }
    } else {
        condition = false
    }
    return if (condition) {
        accumulator
    } else {
        null
    }
}

private fun parseScenario(files: List<String?>, contents: List<String>, accumulator: Accumulator): Accumulator? {
    val scenarioParser = ScenarioJSONParser(accumulator)
    val taskPars = TasksRewardsParser(accumulator)
    var validScenario = scenarioParser.parseGarbage(contents[2]) && scenarioParser.parseEvents(contents[2])
    validScenario = validScenario && taskPars.parseRewards(contents[2]) && taskPars.parseTasks(contents[2])
    if (validScenario) {
        files[2]?.let { Logger.logInitializationInfoSuccess(it) }
    } else {
        return null
    }
    return accumulator
}

private fun validate(files: List<String?>): List<String>? {
    val contents: MutableList<String> = mutableListOf()
    val validatingSchemas: MutableList<String> = mutableListOf(getSchemaPath("schema/map.schema"))
    validatingSchemas.add(getSchemaPath("schema/corporations.schema"))
    validatingSchemas.add(getSchemaPath("schema/scenario.schema"))
    for (i in 0..2) {
        val file: String? = files[i]
        if (file != null) {
            val validatedFile: String = readFile(validatingSchemas[i], file) ?: return null
            contents.add(validatedFile)
        } else {
            break
        }
    }
    return contents
}

private fun getSchemaPath(file: String): String {
    return Thread.currentThread().contextClassLoader.getResource(file)?.toString()
        ?: throw IllegalArgumentException("Schema '$file' not found")
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
        Logger.logInitializationInfoFail(file)
        logger.error(notFound) { "error" }
        condition = false
    }
    if (condition) {
        try {
            var fail: ValidationFailure? = null
            if (objectFile != null) {
                objects = objectFile.readText()
                fail = validator.validate(objects)
            }
            if (fail != null) {
                Logger.logInitializationInfoFail(file)
            } else {
                return null
            }
        } catch (notFound: IOException) {
            logger.error(notFound) { "error" }
            Logger.logInitializationInfoFail(file)
        }
    }
    return null
}
