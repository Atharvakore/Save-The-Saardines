package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Shore
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
            corporations = objects.getJSONArray(CORPORATIONS)
            ships = objects.getJSONArray(SHIPS)
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
            } else {
                return false
            }
        }
        return true
    }

    private fun validateCorporation(corporation: JSONObject): Boolean {
        return corporation.has(ID)
    }

    private fun createShip(ship: JSONObject): Ship {
        val id = ship.getInt(ID)
        val type = ship.getString(TYPE)
        val corporation = ship.getInt(CORPORATION)
        val location = ship.getInt(LOCATION)
        val maxVelocity = ship.getInt(MAXVELOCITY)
        val acceleration = ship.getInt(ACCELERATION)
        val fuelCapacity = ship.getInt(FUELCAPACITY)
        val fuelConsumption = ship.getInt(FUELCONSUMPTION)
        val shipInstance = Ship(
            id,
            maxVelocity,
            acceleration,
            fuelCapacity,
            fuelConsumption,
            mutableListOf(),
        )
        shipInstance.position = accumulator.getTileById(location)!!

        when (type) {
            COLLECTER -> {
                val capacity = ship.getInt(CAPACITY)
                val garbageType = ship.getString(GARBAGETYPE)
                val container = Container(mapGarbageStringToType[garbageType]!!, capacity)
                val collectorCapability = CollectingShip(mutableListOf(container))
                shipInstance.addCapability(collectorCapability)
            }

            SCOUTING -> {
                val visibility = ship.getInt(VISIBILITY)
                val scoutingShip = ScoutingShip(visibility)
                shipInstance.addCapability(scoutingShip)
            }

            else -> {
                val visibility = ship.getInt(VISIBILITY)
                val coordinatingShip = CoordinatingShip(visibility)
                shipInstance.addCapability(coordinatingShip)
            }
        }
        accumulator.addShip(shipInstance.id, shipInstance)
        accumulator.addShipToCorp(corporation, shipInstance.id)
        return shipInstance
    }

    private fun createCorporation(corporation: JSONObject): Corporation {
        val id = corporation.getInt(ID)
        val name = corporation.getString(NAME)
        val ships = corporation.getJSONArray("ships")
        val ownnedShips: MutableList<Ship> = mutableListOf()
        ships.forEach {
            ownnedShips.add(accumulator.ships[it as Int]!!)
        }
        val harbors = corporation.getJSONArray(HOMEHARBORS)
        val ownedHarbors: MutableList<Shore> = mutableListOf()
        harbors.forEach {
            ownedHarbors.add(accumulator.getTileById(it as Int) as Shore)
        }
        val garbageTypes: List<GarbageType> = listOf(GarbageType.OIL, GarbageType.PLASTIC, GarbageType.CHEMICALS)
        val corporationInstance = Corporation(id, name, ownnedShips, ownedHarbors, garbageTypes, mutableListOf())
        return corporationInstance
    }

    private fun validateShip(shipObject: JSONObject): Boolean {
        return shipObject.has(ID)
    }

    private fun validateShips(shipObjects: JSONArray): Boolean {
        shipObjects.forEach {
            if (validateShip(it as JSONObject)) {
                val ship = this.createShip(it)
            } else {
                return false
            }
        }
        return true
    }

    /** String constants for the JSON keys **/
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
        const val VISIBILITY = "visibility"
        const val GARBAGETYPE = "garbageType"
        const val SCOUTING = "SCOUTING"
        const val COLLECTER = "COLLECTING"
        const val COORDINATING = "COORDINATING"
        const val PLASTIC = "PLASTIC"
        const val OIL = "OIL"
        const val CHEMICALS = "CHEMICALS"
        const val HOMEHARBORS = "homeHarbors"
        val mapGarbageStringToType =
            mapOf(CHEMICALS to GarbageType.CHEMICALS, OIL to GarbageType.OIL, PLASTIC to GarbageType.PLASTIC)
    }
}
