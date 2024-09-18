package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.events.EndRestriction
import de.unisaarland.cs.se.selab.events.Event
import de.unisaarland.cs.se.selab.events.OilSpill
import de.unisaarland.cs.se.selab.events.PirateAttack
import de.unisaarland.cs.se.selab.events.Restriction
import de.unisaarland.cs.se.selab.events.Storm
import de.unisaarland.cs.se.selab.ships.Ship
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
import java.io.FileNotFoundException
import java.io.IOException

private val logger = KotlinLogging.logger {}
private val garbageTypes: List<String> = listOf("PLASTIC", "OIL", "CHEMICALS")
private var dummyId: Int = -1

/**
 * ScenarioParser aka, parsing events and garbage
 */
class ScenarioJSONParser(override val accumulator: Accumulator) : JSONParser {
    private val id: String = "id"
    private val type: String = "type"
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
            if (event != null && !validateEvent(event as JSONObject)) {
                return false
            }
        }
        return true
    }

    private fun validateEvent(event: JSONObject): Boolean {
        val eventId: Int = event.getInt(id)
        val uniqueId: Boolean = eventId >= 0 && accumulator.events[eventId] == null
        val eventTick: Int = event.getInt("tick")
        if (uniqueId && eventTick >= 0) {
            return createEvent(event)
        }
        return false
    }

    private fun createEvent(event: JSONObject): Boolean {
        val eventId = event.getInt(id)
        val eventType: String = event.getString(type)
        val eventTick: Int = event.getInt("tick")
        val eventLocation: Int
        var eventRadius: Int? = null
        var eventTile: Tile? = null
        if (eventType != "PIRATE_ATTACK") {
            eventLocation = event.getInt(location)
            eventTile = accumulator.tiles[eventLocation] ?: return false
            eventRadius = event.getInt("radius")
        }
        var condition = true
        when (eventType) {
            "STORM" -> {
                val eventSpeed: Int = event.getInt("speed")
                val eventDirection: Direction? = Direction.getDirection(event.getInt("direction"))
                val storm =
                    Storm(eventId, eventTick, accumulator.map, eventTile, eventRadius!!, eventSpeed)
                eventDirection.let {
                    if (it != null) {
                        storm.direction = it
                    }
                }
                accumulator.addEvent(eventId, storm)
            }

            "RESTRICTION" -> {
                val eventDuration: Int = event.getInt("duration")
                if (eventDuration > 0) {
                    val restriction: Event =
                        Restriction(eventId, eventTick, accumulator.map, eventTile!!, eventRadius!!)
                    val endRestriction: Event =
                        EndRestriction(dummyId, eventTick + eventDuration, accumulator.map, eventTile, eventDuration)
                    accumulator.addEvent(eventId, restriction)
                    accumulator.addEvent(dummyId--, endRestriction)
                } else {
                    condition = false
                }
            }

            "OIL_SPILL" -> {
                val eventAmount: Int = event.getInt("amount")
                if (accumulator.map != null) {
                    val oilSpill =
                        OilSpill(eventId, eventTick, accumulator.map!!, eventTile!!, eventRadius!!, eventAmount)
                    accumulator.addEvent(eventId, oilSpill)
                }
            }

            "PIRATE_ATTACK" -> {
                val eventShip: Int = event.getInt("shipID")
                val ship: Ship? = accumulator.ships[eventShip]
                if (ship != null) {
                    val shipCorp: Corporation = ship.owner
                    accumulator.addEvent(eventId, PirateAttack(eventId, eventTick, ship, shipCorp))
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
            if (gar != null && !validateGarbage(gar as JSONObject)) {
                return false
            }
        }
        return true
    }

    private fun validateGarbage(garbage: JSONObject): Boolean {
        val garbageId: Int = garbage.getInt(id)
        val garbageType: String = garbage.getString("type")
        val typeExists: Boolean = garbageTypes.contains(garbageType)
        val uniqueId: Boolean = garbageId >= 0 && accumulator.garbage[garbageId] == null
        val location: Tile? = accumulator.tiles[garbage.getInt(location)]
        val exists: Boolean = location != null && typeExists
        var locationIsNotLand: Boolean = location is Shore || location is ShallowOcean
        if (garbageType != "CHEMICALS") {
            locationIsNotLand = locationIsNotLand || location is DeepOcean
        }
        if (uniqueId && exists && locationIsNotLand) {
            return createGarbageObj(garbage)
        }
        return false
    }

    private fun createGarbageObj(garbage: JSONObject): Boolean {
        val garbageId: Int = garbage.getInt(id)
        val garbageType: GarbageType = GarbageType.valueOf(garbage.getString(type))
        val garbageLocation: Tile = accumulator.tiles[garbage.getInt(location)] ?: return false
        val amount: Int = garbage.getInt("amount")
        val g = Garbage(garbageId, amount, garbageType, mutableSetOf())
        accumulator.addGarbage(garbageId, g)
        garbageLocation.addGarbage(g)
        return true
    }
}
