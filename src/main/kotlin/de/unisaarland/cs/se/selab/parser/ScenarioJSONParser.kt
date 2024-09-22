package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.events.EndRestriction
import de.unisaarland.cs.se.selab.events.Event
import de.unisaarland.cs.se.selab.events.OilSpill
import de.unisaarland.cs.se.selab.events.PirateAttack
import de.unisaarland.cs.se.selab.events.Restriction
import de.unisaarland.cs.se.selab.events.Storm
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.ships.THOUSAND
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import io.github.oshai.kotlinlogging.KotlinLogging
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

private val logger = KotlinLogging.logger {}
private val garbageTypes: List<String> = listOf("PLASTIC", "OIL", "CHEMICALS")
private var dummyId: Int = -1

/**
 * ScenarioParser aka, parsing events and garbage
 */
class ScenarioJSONParser(override val accumulator: Accumulator) : JSONParser {
    private val id: String = "id"
    private val type: String = TYPE
    private val location: String = "location"

    /**
     * function called from Main, parser the events and passes them to validators
     */
    fun parseEvents(eventsFile: String): Boolean {
        try {
            val events = JSONObject(eventsFile).getJSONArray("events")
            return createEvents(events)
        } catch (e: IOException) {
            logger.error { e }
        }
        return false
    }

    private fun createEvents(events: JSONArray): Boolean {
        for (event in events) {
            if (!validateEvent((event ?: error("event Should be...")) as JSONObject)) {
                return false
            }
        }
        return true
    }

    private fun validateEvent(event: JSONObject): Boolean {
        val eventId: Int = event.getInt(id)
        val uniqueId: Boolean = eventId >= 0 && accumulator.events[eventId] == null
        val eventTick: Int = event.getInt(TICK)
        if (uniqueId && eventTick >= 0) {
            return onlyNecessaryProperties(event) && createEvent(event)
        }
        return false
    }

    private fun onlyNecessaryProperties(event: JSONObject): Boolean {
        val eventType = event.getString(TYPE)
        var result = true
        when (eventType) {
            "STORM" -> result = result && event.keySet() == requiredByStorm
            "RESTRICTION" -> result = result && event.keySet() == requiredByRestriction
            "OIL_SPILL" -> result = result && event.keySet() == requiredByOil
            PIRATEATTACK -> result = event.keySet() == requiredKeysByAttack
        }
        return result
    }

    private fun createEvent(event: JSONObject): Boolean {
        val id = event.getInt(id)
        val type: String = event.getString(type)
        val tick: Int = event.getInt("tick")
        val location: Int
        var radius: Int = 0
        var tile: Tile? = null
        if (type != PIRATEATTACK) {
            location = event.getInt(this.location)
            tile = accumulator.tiles[location]
            radius = requireNotNull(event.getInt("radius"))
        }
        var condition = true
        when (type) {
            "STORM" -> {
                val eventSpeed: Int = event.getInt(SPEED)
                val eventDirection: Direction = requireNotNull(Direction.getDirection(event.getInt("direction")))
                val storm =
                    Storm(id, tick, accumulator.map, requireNotNull(tile), radius, eventSpeed)
                storm.direction = eventDirection
                accumulator.addEvent(id, storm)
            }

            "RESTRICTION" -> {
                val eventDuration: Int = event.getInt(DURATION)
                if (eventDuration > 0) {
                    val restriction: Event =
                        Restriction(id, tick, accumulator.map, requireNotNull(tile), radius)
                    val endRestriction: Event =
                        EndRestriction(dummyId, tick + eventDuration, accumulator.map, tile, eventDuration)
                    accumulator.addEvent(id, restriction)
                    accumulator.addEvent(dummyId--, endRestriction)
                } else {
                    condition = false
                }
            }

            "OIL_SPILL" -> {
                val eventAmount: Int = event.getInt(AMOUNT)
                val oilSpill =
                    OilSpill(id, tick, requireNotNull(accumulator.map), requireNotNull(tile), radius, eventAmount)
                accumulator.addEvent(id, oilSpill)
            }

            "PIRATE_ATTACK" -> {
                val eventShip: Int = event.getInt("shipID")
                val ship: Ship? = accumulator.ships[eventShip]
                if (ship != null) {
                    val shipCorp: Corporation = ship.owner
                    accumulator.addEvent(id, PirateAttack(id, tick, ship, shipCorp))
                } else {
                    condition = false
                }
            }

            else -> {
                condition = false
            }
        }
        return condition
    }

    /** function called from Main, parser the garbage and passes them to validators */
    fun parseGarbage(garbageJSON: String): Boolean {
        try {
            val garbage = JSONObject(garbageJSON).getJSONArray("garbage")
            return createGarbage(garbage)
        } catch (e: IOException) {
            logger.error { e }
        }
        return false
    }

    private fun createGarbage(garbage: JSONArray): Boolean {
        for (gar in garbage) {
            if (!validateGarbage((gar ?: error("Garbage should be...")) as JSONObject)) {
                return false
            }
        }
        return true
    }

    private fun validateGarbage(garbage: JSONObject): Boolean {
        val garbageId: Int = garbage.getInt(id)
        val garbageType: String = garbage.getString(TYPE)
        val typeExists: Boolean = garbageTypes.contains(garbageType)
        val uniqueId: Boolean = garbageId >= 0 && accumulator.garbage[garbageId] == null
        val locationId: Int = garbage.getInt(location)
        val location: Tile? = accumulator.tiles[locationId]
        val exists: Boolean = location != null && locationId >= 0 && typeExists
        var condition: Boolean = uniqueId && garbage.getInt(AMOUNT) > 0 && locationId >= 0
        var locationIsNotLand: Boolean = location is Shore || location is ShallowOcean
        if (garbageType != "CHEMICALS") {
            locationIsNotLand = locationIsNotLand || location is DeepOcean
        }
        if (garbageType == "OIL") {
            condition = condition && garbage.getInt(AMOUNT) <= THOUSAND
        }
        if (condition && exists && locationIsNotLand) {
            return createGarbageObj(garbage)
        }
        return false
    }

    private fun createGarbageObj(garbage: JSONObject): Boolean {
        val garbageId: Int = garbage.getInt(id)
        val garbageType: GarbageType = GarbageType.valueOf(garbage.getString(type))
        val garbageLocation: Tile = accumulator.tiles[garbage.getInt(location)] ?: return false
        val amount: Int = garbage.getInt(AMOUNT)
        val g = Garbage(garbageId, amount, garbageType, mutableSetOf())
        accumulator.addGarbage(garbageId, g)
        garbageLocation.addGarbage(g)
        accumulator.map.garbageOnMap += amount
        return true
    }

    /** All strings */
    companion object {
        const val ID = "id"
        const val TYPE = "type"
        const val TICK = "tick"
        const val LOCATION = "location"
        const val SHIPID = "shipID"
        const val PIRATEATTACK: String = "PIRATE_ATTACK"
        const val AMOUNT: String = "amount"
        const val DIRECTION: String = "direction"
        const val SPEED: String = "speed"
        const val RADIUS: String = "radius"
        const val DURATION: String = "duration"
        val requiredKeysByDefault = setOf(ID, TYPE, TICK)
        val requiredKeysByAttack = requiredKeysByDefault + SHIPID
        val requiredByLocalEvents = requiredKeysByDefault + LOCATION + RADIUS
        val requiredByRestriction = requiredByLocalEvents + DURATION
        val requiredByOil = requiredByLocalEvents + AMOUNT
        val requiredByStorm = requiredByLocalEvents + SPEED + DIRECTION
    }
}
