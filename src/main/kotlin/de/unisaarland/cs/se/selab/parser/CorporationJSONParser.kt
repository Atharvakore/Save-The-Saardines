package de.unisaarland.cs.se.selab.parser
import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.Ship
import io.github.oshai.kotlinlogging.KotlinLogging
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/** Parser and Validator for extracting data from Map File **/
class CorporationJSONParser(override val accumulator: Accumulator) : JSONParser {
    private val logger = KotlinLogging.logger {}

    /** Parsing function **/
    public fun parseCorporationsFile(filePath: String): Boolean {
        val file: File
        val corporations: JSONArray
        val ships: JSONArray
        val objects: JSONObject
        try {
            file = File(filePath)
        } catch (err: FileNotFoundException) {
            logger.error { "file '$filePath' does not exist." }
            return false
        }
        try {
            objects = JSONObject(file.readText())
            if (!objects.has(CORPORATIONS) || !objects.has(SHIPS)) {
                return false
            }
            corporations = objects.getJSONObject(CORPORATIONS) as JSONArray
            ships = objects.getJSONObject(SHIPS) as JSONArray
        } catch (err: IOException) {
            logger.error { "Failed to parse file '$filePath'" }
            return false
        }
        val result = validateShips(ships) && validateCorporations(corporations)
        return result
    }

    private fun validateCorporations(corpObjects: JSONArray): Boolean {
        return false
    }

    private fun validateCorporation(corporation: JSONObject): Boolean {
        return false
    }

    private fun createShip(ship: JSONObject): Ship {
        return  Ship()
    }

    private fun createCorporation(corporation: JSONObject): Corporation {
        return Corporation()
    }

    private fun validateShip(shipObject: JSONObject): Boolean {
        return false
    }

    private fun validateShips(corporationObjects: JSONArray): Boolean {
        return false
    }

    companion object Companion {
        const val CORPORATIONS = "corporations"
        const val SHIPS = "ships"
    }
}
