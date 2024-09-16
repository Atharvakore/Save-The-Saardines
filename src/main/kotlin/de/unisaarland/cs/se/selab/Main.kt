package de.unisaarland.cs.se.selab

import de.unisaarland.cs.se.selab.parser.Accumulator
import de.unisaarland.cs.se.selab.parser.MapJSONParser

/**
 * TODO: Implement the main method.
 */
fun main(args: Array<String>) {
    var mapFile: String? = null
    var corporationsFile: String? = null
    var ScenarioFile: String? = null
    var maxTicks: Int? = null
    var outputFile: String? = "stdout"
   for (i in 0..args.size){
       when (args[i]){
           "--map" -> mapFile = args[i+1]
           "--corporations" -> corporationsFile = args[i+1]
           "--scenario" -> ScenarioFile = args[i+1]
           "--max_ticks" -> maxTicks = args[i+1].toInt()
           "--out" -> outputFile = args[i+1]
           "--help" -> continue
       }
   }
}
