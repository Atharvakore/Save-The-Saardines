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
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

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
private fun parse(files: List<String?>, maxTicks: Int?, outputFile: String?): Accumulator? {
    val contents = validate(files) ?: return null
    val accumulator = Accumulator()
    val mapParser = MapJSONParser(accumulator)
    if (maxTicks != null && mapParser.parseMap(contents[0])) {
        Logger.logInitializationInfoSuccess(files[0]!!)
    } else {
        return null
    }
    val corpParser = CorporationJSONParser(accumulator)
    if (corpParser.parseCorporationsFile(contents[1])) {
        Logger.logInitializationInfoSuccess(files[1]!!)
    } else {
        return null
    }
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
    val validatingSchemas: MutableList<String> = mutableListOf("resources/schema/map.schema")
    validatingSchemas.add("resources/schema/corporations.schema")
    validatingSchemas.add("resources/schema/scenario.schema")
    var objectFile: File
    for (i in 0..2) {
        val file: String? = files[i]
        if (file != null) {
            val schema: Schema = SchemaLoader.forURL(validatingSchemas[i]).load()
            val validator: Validator = Validator.forSchema(schema)
            var objects: String
            try {
                objectFile = File(file)
            } catch (notFound: FileNotFoundException) {
                Logger.logInitializationInfoFail(file)
                return null
            }
            try {
                objects = objectFile.readText()
                val fail: ValidationFailure? = validator.validate(objects)
                if (fail != null) {
                    Logger.logInitializationInfoFail(file)
                    return null
                } else {
                    contents.add(objects)
                }
            } catch (notFound: IOException) {
                Logger.logInitializationInfoFail(file)
            }
        } else {
            return null
        }
    }
    return contents
}
