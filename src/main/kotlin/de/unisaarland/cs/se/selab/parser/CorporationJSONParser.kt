package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.CollectingShip
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
            logger.error(err) { "file '$filePath' does not exist." }
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
            logger.error(err) { "Failed to parse file '$filePath'" }
            return false
        }
        val result = validateShips(ships) && validateCorporations(corporations)
        return result
    }

    private fun validateCorporations(corpObjects: JSONArray): Boolean {
        corpObjects.forEach {
            if (validateCorporation(it as JSONObject)) {
                this.createCorporation(it)
            } else return false
        }
    }

    private fun validateCorporation(corporation: JSONObject): Boolean {
        return corporation.has(ID)
    }

    private fun createShip(ship: JSONObject): Ship {
        val id = ship.getInt(ID)
        val name = ship.getString(NAME)
        val type = ship.getString(TYPE)
        val corporation = ship.getInt(CORPORATION)
        val location = ship.getInt(LOCATION)
        val maxVelocity = ship.getString(MAXVELOCITY)
        val acceleration = ship.getString(ACCELERATION)
        val fuelCapacity = ship.getString(FUELCAPACITY)
        val fuelConsumption = ship.getString(FUELCONSUMPTION)
        val ship:Ship =  Ship(id,name,type,corporation,maxVelocity, acceleration, fuelCapacity, fuelConsumption, mutableListOf())
        when (type) {
            COLLECTER -> {

            }

            SCOUTING -> {

            }

            else -> {

            }
        }
        accumulator.addShip(ship.id, ship)
        accumulator.addShipToCorp(corporation, ship.id)

    }

    private fun createCorporation(corporation: JSONObject): Corporation {
        return Corporation()
    }

    private fun validateShip(shipObject: JSONObject): Boolean {
        return shipObject.has(ID)
    }

    private fun validateShips(shipObjects: JSONArray): Boolean {
        shipObjects.forEach {
            if (validateShip(it as JSONObject)) {
                val ship = this.createShip(it)
            } else return false
        }
        return true
    }

    companion object Companion {
        const val CORPORATIONS = "corporations"
        const val SHIPS = "ships"
        const val ID = "id"
        const val NAME = "name"
        const val TYPE = "type"
        const val CORPORATION = "corporation"
        const val LOCATION = "location"
        const val MAXVELOCITY = "maxVelocity"
        const val ACCELERATION = "acceleration"
        const val FUELCAPACITY = "fuelCapacity"
        const val FUELCONSUMPTION = "fuelConsumption"
        const val CAPACITY = "capacity"
        const val GARBAGETYPE = "garbageType"
        const val SCOUTING = "SCOUTING"
        const val COLLECTER = "COLLECTING"
        const val COORDINATING = "COORDINATING"

    }
}
