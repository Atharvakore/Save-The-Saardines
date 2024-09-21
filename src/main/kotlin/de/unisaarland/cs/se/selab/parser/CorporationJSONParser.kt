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
import java.io.IOException

/** Parser and Validator for extracting data from Map File **/
class CorporationJSONParser(override val accumulator: Accumulator) : JSONParser {
    private val logger = KotlinLogging.logger {}
    private val ownedHarbors: MutableSet<Int> = mutableSetOf()

    /** Parsing function **/
    fun parseCorporationsFile(filePath: String): Boolean {
        val corporations: JSONArray
        val ships: JSONArray
        val objects: JSONObject
        try {
            objects = JSONObject(filePath)
            return if (objects.has(CORPORATIONS) && objects.has(SHIPS)) {
                corporations = objects.getJSONArray(CORPORATIONS)
                ships = objects.getJSONArray(SHIPS)
                validateShips(ships) &&
                    validateCorporations(corporations) &&
                    accumulator.mapCorporationToShips.isEmpty() &&
                    harborAtLeastOneCorp()
            } else {
                false
            }
        } catch (err: IOException) {
            logger.error(err) { "Failed to parse file '$filePath'" }
            return false
        }
    }

    private fun harborAtLeastOneCorp(): Boolean {
        return accumulator.listOfHarbors
            .all { accumulator.corporations.values.any { corp -> corp.ownedHarbors.contains(it) } }
    }

    private fun validateCorporations(corpObjects: JSONArray): Boolean {
        corpObjects.forEach {
            if (validateCorporation((it ?: "corporation is Null") as JSONObject)) {
                this.createCorporation((it ?: "corporation shouldn't be null") as JSONObject)
            } else {
                return false
            }
        }
        return true
    }

    private fun checkHarborTile(harbor: Any): Boolean {
        val id = harbor as Int
        return accumulator.listOfHarbors.any { it.id == id }
    }

    private fun manageGarbage(corporation: JSONObject): List<GarbageType> {
        val garbageTypes = corporation.getJSONArray(GARBAGETYPES)
        val list: List<GarbageType> = garbageTypes.map { type ->
            mapGarbageStringToType[type]
                ?: error("Impossible... We have this type")
        }

        return list
    }

    private fun validateCorporation(corporation: JSONObject): Boolean {
        if (corporation.keySet() != requiredCorporationKeys) return false
        var result = true
        result = result && accumulator.corporations[corporation.getInt(ID)] == null
        val shipsSet = corporation.getJSONArray(SHIPS).toSet()
        result = result && shipsSet == (
            accumulator.mapCorporationToShips[corporation.getInt(ID)]?.toSet()
                ?: return false
            )

        val id = corporation.getInt(ID)
        val name = corporation.getString(NAME)
        val ships = corporation.getJSONArray(SHIPS)
        result = result && ships.length() > 0
        val harbors = corporation.getJSONArray(HOMEHARBORS)
        result = result && harbors.length() > 0
        val garbage = manageGarbage(corporation)

        // check for uniqueness
        result = result && id > 0 && !accumulator.corporations.containsKey(id)
        result = result && accumulator.corporations.values.none { corp -> corp.name == name }
        // check if tiles for harbors exists
        harbors.forEach { harbor -> result = result && checkHarborTile(harbor) }
        // check if ships belongs to corporation
        val shipsInAcc = accumulator.mapCorporationToShips[id]
        result = result && shipsInAcc != null && shipsInAcc.size == ships.length()
        result = result && checkShipsInCorporation(ships, shipsInAcc, garbage, id)
        return result
    }

    private fun checkShipsInCorporation(
        ships: JSONArray,
        shipsInAcc: MutableList<Int>?,
        garbage: List<GarbageType>,
        id: Int
    ): Boolean {
        var result = true
        ships.forEach { ship ->
            val shipId = (ship ?: error("It should be a shipID")) as Int
            result = result && shipsInAcc?.contains(shipId) == true
        }
        // check if there is at least one ship for each garbageType
        val shipList = accumulator.mapCorporationToShips[id] ?: error("IMPOSSIBLE... There should be this list")
        val shipsInCorp = accumulator.ships.filter { ship -> shipList.contains(ship.key) }.values
        val typesPresent = mutableSetOf<GarbageType>()
        val collectingShips = shipsInCorp.filter { ship ->
            ship.capabilities.filterIsInstance<CollectingShip>().isNotEmpty()
        }
        collectingShips.forEach { ship ->
            val collectingCapability: CollectingShip = ship.capabilities[0] as CollectingShip
            typesPresent.addAll(collectingCapability.garbageTypes())
        }
        if (typesPresent != garbage.toSet()) result = false
        // check if garbage is empty and list has no collector
        if (garbage.isEmpty()) {
            shipList.forEach { shipId ->
                val ship = accumulator.ships[shipId] ?: error("ship Should exist")
                result = result && ship.capabilities.filterIsInstance<CollectingShip>().none()
            }
        }
        // collect all collecting Ships
        if (garbage.isNotEmpty() && collectingShips.isEmpty()) return false

        return result
    }

    private fun createShip(ship: JSONObject): Ship {
        val id = ship.getInt(ID)
        val type = ship.getString(TYPE)
        val location = ship.getInt(LOCATION)
        val owner = ship.getInt(CORPORATION)
        val maxVelocity = ship.getInt(MAXVELOCITY)
        val acceleration = ship.getInt(ACCELERATION)
        val fuelCapacity = ship.getInt(FUELCAPACITY)
        val fuelConsumption = ship.getInt(FUELCONSUMPTION)
        val name = ship.getString(NAME)
        val shipInstance = Ship(
            id,
            maxVelocity,
            acceleration,
            fuelCapacity,
            fuelConsumption,
            mutableListOf(),
        )
        shipInstance.position = requireNotNull(accumulator.tiles[location])
        shipInstance.name = name
        when (type) {
            COLLECTER -> {
                val capacity = ship.getInt(CAPACITY)
                val garbageType = ship.getString(GARBAGETYPE)
                val container = Container(requireNotNull(mapGarbageStringToType[garbageType]), capacity)
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
        val corporationShips: MutableList<Int>? = accumulator.mapCorporationToShips[ship.getInt(CORPORATION)]
        if (corporationShips != null) {
            corporationShips.add(id)
        } else {
            accumulator.mapCorporationToShips[owner] = mutableListOf(id)
        }
        return shipInstance
    }

    private fun checkShipUnique(id: Int, name: String): Boolean {
        var result = true
        result = result && id >= 0
        result = result && !accumulator.ships.containsKey(id)
        result = result && accumulator.ships.none { it.value.name == name }

        return result
    }

    private fun checkShipProperties(
        type: String,
        velocity: Int,
        acceleration: Int,
        fuel: Int,
        consumption: Int
    ): Boolean {
        var result = velocity >= TEN && acceleration >= FIVE && fuel >= K3 && consumption >= FIVE
        when (type) {
            COLLECTER -> {
                result = result && velocity <= FIFTY && consumption <= NINE
                result = result && acceleration <= TEN && fuel <= K5
            }

            SCOUTING -> {
                result = result && velocity <= HUNDRED && consumption >= SEVEN && consumption <= TEN
                result = result && acceleration <= N25 && fuel <= K10
            }

            else -> {
                result = result && velocity <= N50 && consumption <= SEVEN
                result = result && acceleration <= N15 && fuel <= K5
            }
        }
        return result
    }

    private fun createCorporation(corporation: JSONObject): Corporation {
        val id = corporation.getInt(ID)
        val name = corporation.getString(NAME)
        val ships = corporation.getJSONArray("ships")
        val ownedShips: MutableList<Ship> = mutableListOf()
        ships.forEach {
            ownedShips.add(
                requireNotNull(accumulator.ships[(it ?: error("ship shouldn't be null in parser case")) as Int])
            )
        }
        val harbors = corporation.getJSONArray(HOMEHARBORS).toSet()
        val ownedHarbors: MutableList<Shore> = mutableListOf()
        harbors.forEach {
            ownedHarbors.add(
                (
                    accumulator.tiles[(it ?: error("This is not a number")) as Int]
                        ?: error("There should exists this tile")
                    ) as Shore
            )
        }
        val garbageTypes: List<GarbageType> = listOf(GarbageType.OIL, GarbageType.PLASTIC, GarbageType.CHEMICALS)
        val corporationInstance = Corporation(id, name, ownedShips, ownedHarbors, garbageTypes, mutableListOf())
        ownedShips.forEach {
            it.owner = corporationInstance
        }

        accumulator.addCorporation(id, corporationInstance)
        accumulator.mapCorporationToShips.remove(id)
        return corporationInstance
    }

    private fun validateShip(shipObject: JSONObject): Boolean {
        val id = shipObject.getInt(ID)
        val name = shipObject.getString(NAME)
        val type = shipObject.getString(TYPE)
        val location = shipObject.getInt(LOCATION)
        val maxV = shipObject.getInt(MAXVELOCITY)
        val accel = shipObject.getInt(ACCELERATION)
        val fuel = shipObject.getInt(FUELCAPACITY)
        val consumption = shipObject.getInt(FUELCONSUMPTION)
        var result: Boolean = true
        result = result && checkShipUnique(id, name)
        result = result && checkShipProperties(type, maxV, accel, fuel, consumption)
        val tile = accumulator.tiles[location]
        result = result && tile != null
        when (type) {
            COLLECTER -> {
                result = result && shipObject.keySet() == requiredCollectingKeys
                val garbageType = shipObject.optString(GARBAGETYPE, null)
                result = result && garbageType != null
                val capacity = shipObject.optInt(CAPACITY, -1)
                result = result && capacity > 0
                result = result && checkGarbageCapacity(garbageType, capacity)
            }

            SCOUTING -> {
                result = result && shipObject.keySet() == requiredVisibilityKeys
                val visibility = shipObject.optInt(VISIBILITY, -1)
                result = result && visibility in 2..FIVE
            }

            else -> {
                result = result && shipObject.keySet() == requiredVisibilityKeys
                val visibility = shipObject.optInt(VISIBILITY, -1)
                result = result && visibility == 1
            }
        }
        return result
    }

    private fun checkGarbageCapacity(garbageType: String, capacity: Int): Boolean {
        when (garbageType) {
            PLASTIC -> {
                return capacity in K1..K5
            }

            OIL -> {
                return capacity in K50..K100
            }

            else -> {
                return capacity in K1..K10
            }
        }
    }

    private fun validateShips(shipObjects: JSONArray): Boolean {
        shipObjects.forEach {
            if (validateShip((it ?: error("There should be a ship")) as JSONObject)) {
                val ship = this.createShip(it as JSONObject)
                accumulator.addShip(ship.id, ship)
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
        const val VISIBILITY = "visibilityRange"
        const val GARBAGETYPE = "garbageType"
        const val GARBAGETYPES = "garbageTypes"
        const val SCOUTING = "SCOUTING"
        const val COLLECTER = "COLLECTING"
        const val COORDINATING = "COORDINATING"
        const val PLASTIC = "PLASTIC"
        const val OIL = "OIL"
        const val CHEMICALS = "CHEMICALS"
        const val HOMEHARBORS = "homeHarbors"
        const val K5 = 5000
        const val K3 = 3000
        const val TEN = 10
        const val FIVE = 5
        const val FIFTY = 50
        const val N3 = 3
        const val HUNDRED = 100
        const val SEVEN = 7
        const val N25 = 25
        const val N50 = 100
        const val K10 = 10000
        const val K1 = 1000
        const val K50 = 50000
        const val K100 = 100000
        const val N15 = 15
        const val NINE = 9
        val shipTypes = listOf(COLLECTER, SCOUTING, COORDINATING)
        val requiredCorporationKeys = setOf(ID, NAME, SHIPS, HOMEHARBORS, GARBAGETYPES)
        val requiredShipsKeys =
            setOf(ID, NAME, TYPE, CORPORATION, LOCATION, MAXVELOCITY, ACCELERATION, FUELCAPACITY, FUELCONSUMPTION)
        val requiredCollectingKeys = requiredShipsKeys + CAPACITY + GARBAGETYPE
        val requiredVisibilityKeys = requiredShipsKeys + VISIBILITY

        val mapGarbageStringToType =
            mapOf(CHEMICALS to GarbageType.CHEMICALS, OIL to GarbageType.OIL, PLASTIC to GarbageType.PLASTIC)
    }
}
