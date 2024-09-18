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
    if (acc != null) {
        val sim = Simulation(acc.corporations.values.toList(), acc.events.values.toList(), maxTicks!!, acc.map!!)
        sim.start()
    }
}

/** The main function for parsing */
fun parse(files: List<String?>, maxTicks: Int?, outputFile: String?): Accumulator? {
    val contents = validate(files)
    val accumulator = Accumulator()
    var cond: Boolean = maxTicks == null || outputFile == null || contents == null
    cond = cond || parseMap(files, contents!!, accumulator) == null
    if (cond || parseScenario(files, contents!!, accumulator) == null) {
        return null
    }
    return accumulator
}

private fun parseMap(files: List<String?>, contents: List<String>, accumulator: Accumulator): Accumulator? {
    var condition: Boolean = true
    val mapParser = MapJSONParser(accumulator)
    if (mapParser.parseMap(contents[0])) {
        Logger.logInitializationInfoSuccess(files[0]!!)
    } else {
        condition = false
    }
    val corpParser = CorporationJSONParser(accumulator)
    if (corpParser.parseCorporationsFile(contents[1])) {
        Logger.logInitializationInfoSuccess(files[1]!!)
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
        Logger.logInitializationInfoSuccess(files[2]!!)
    } else {
        return null
    }
    return accumulator
}

private fun validate(files: List<String?>): List<String>? {
    val contents: MutableList<String> = mutableListOf()
    val validatingSchemas: MutableList<String> = mutableListOf("file:///home/badr/Documents/4.SEMESTER/SELab/Project/repo/src/main/resources/schema/map.schema")
    validatingSchemas.add("file:///home/badr/Documents/4.SEMESTER/SELab/Project/repo/src/main/resources/schema/corporations.schema")
    validatingSchemas.add("file:///home/badr/Documents/4.SEMESTER/SELab/Project/repo/src/main/resources/schema/scenario.schema")
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

private fun readFile(validatingSchema: String, file: String): String? {
    val schema: Schema = SchemaLoader.forURL(validatingSchema).load()
    val validator: Validator = Validator.forSchema(schema)
    var objects: String
    var objectFile: File? = null
    var condition: Boolean = true
    try {
        objectFile = File(file)
    } catch (notFound: FileNotFoundException) {
        Logger.logInitializationInfoFail(file)
        logger.error(notFound) { "error" }
        condition = false
    }
    if (condition) {
        try {
            objects = objectFile!!.readText()
            val fail: ValidationFailure? = validator.validate(objects)
            if (fail != null) {
                Logger.logInitializationInfoFail(file)
            } else {
                return objects
            }
        } catch (notFound: IOException) {
            logger.error(notFound) { "error" }
            Logger.logInitializationInfoFail(file)
        }
    }
    return null
}
