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
 * TODO: Implement the main method.
 */
fun main(args: Array<String>) {
    var mapFile: String? = null
    var corporationsFile: String? = null
    var scenarioFile: String? = null
    var maxTicks: Int? = null
    var outputFile: String? = "stdout"
    for (i in 0..args.size) {
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
        //TODO: Create SCENARIO
    }
}

private fun validate(files: List<String?>): List<String>? {
    var successfullyParsed: Boolean = true
    val contents: MutableList<String> = mutableListOf()
    for (file in files){
        if (file != null) {
            val schema: Schema = SchemaLoader.forURL("classpath:///path/to/your/schema.json").load()
            val validator: Validator = Validator.forSchema(schema)
            var objects: String
            try {
                objects = File(file).readText()
            } catch (ioException: IOException) {
                //TODO: Log file failure
                return null
            } catch (notFound: FileNotFoundException) {
                successfullyParsed = false
                //TODO: Log failure
                return null
            }
            val fail: ValidationFailure? = validator.validate(objects)
            if (fail != null) {
                //TODO: LOG FILE FAILURE
                successfullyParsed = false
                return null
            }
            else {
                contents.add(objects)
            }
        }
        else {
            return null
        }
    }
    return contents
}
fun parse(files: List<String?>, maxTicks: Int?, outputFile: String?): Accumulator? {
    val contents = validate(files) ?: return null
    val accumulator = Accumulator()
    val mapParser = MapJSONParser(accumulator)
    if (mapParser.parseMap(contents[0])) {
        //TODO: log file succesfuly parsed
    }
    else {
        return null
    }
    val corpParser = CorporationJSONParser(accumulator)
    if (corpParser.parseCorporationsFile(contents[1])) {
        //TODO: LOG FILE
    }
    else {
        return null
    }
    val scenarioParser = ScenarioJSONParser(accumulator)
    val taskPars = TasksRewardsParser(accumulator)
    var validScenario = scenarioParser.parseGarbage(contents[2]) && scenarioParser.parseEvents(contents[2])
    validScenario = validScenario && taskPars.parseRewards(contents[2]) && taskPars.parseTasks(contents[2])
    if (validScenario){
        //TODO: LOG FILE
    }
    else {
        return null
    }
    return accumulator
}
