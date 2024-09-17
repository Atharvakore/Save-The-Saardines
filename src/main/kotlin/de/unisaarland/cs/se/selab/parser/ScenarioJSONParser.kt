package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.corporation.Corporation
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
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

private val logger = KotlinLogging.logger {}

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
            val events = JSONObject(File(eventsFile).readText()).getJSONArray("events")
            return createEvents(events)
        } catch (e: IOException) {
            logger.error { e }
        } catch (error: FileNotFoundException) {
            logger.error { error }
        }
        return false
    }
    private fun createEvents(events: JSONArray): Boolean {
        for (event in events) {
            if (!validateEvent(event as JSONObject)) {
                return false
            }
        }
        return true
    }

    private fun validateEvent(event: JSONObject): Boolean {
        val uniqueId: Boolean = accumulator.events[event.getInt(id)] == null
        // val event : Boolean = accumulator.getShipsbyId(event.getInt("shipID")) != null

        if (uniqueId) {
            return createEvent(event)
        }
        return false
    }
    private fun createEvent(event: JSONObject): Boolean {
        val eventId = event.getInt(id)
        val eventType: String = event.getString(type)
        val eventTick: Int = event.getInt("tick")
        val eventLocation: Int
        var eventTile: Tile? = null
        if (eventType != "PIRATE_ATTACK"){
            eventLocation = event.getInt(location)
            eventTile = accumulator.getTileById(eventLocation) ?: return false
        }
        var condition: Boolean = true
        when (eventType) {
            "STORM" -> {
                val eventRadius: Int = event.getInt("radius")
                val eventSpeed: Int = event.getInt("speed")
                val eventDirection: Direction? = Direction.getDirection(event.getInt("direction"))
                val storm: Storm = Storm(eventId, eventTick, accumulator.map, eventTile!!, eventRadius)
                storm.setSpeed(eventSpeed)
                storm.setDirection(eventDirection)
                accumulator.addEvent(eventId, storm)
            }
            "RESTRICTION" -> {
                val eventDuration: Int = event.getInt("duration")
                val restriction: Event = Restriction(eventId, eventTick, accumulator.map, eventTile!!, eventDuration)
                accumulator.addEvent(eventId, restriction)
            }
            "OIL_SPILL" -> {
                val eventRadius: Int = event.getInt("radius")
                val eventAmount: Int = event.getInt("amount")
                if (accumulator.map != null) {
                    val oilSpill =
                        OilSpill(eventId, eventTick, accumulator.map!!, eventTile!!, eventRadius, eventAmount)
                    accumulator.addEvent(eventId, oilSpill)
                }
            }
            "PIRATE_ATTACK" -> {
                val eventShip: Int = event.getInt("shipID")
                val ship: Ship? = accumulator.ships[eventShip]
                if (ship != null) {
                    val shipCorp: Corporation = ship.getOwnerCorporation()
                    accumulator.addEvent(eventId, PirateAttack(eventId, eventTick, ship, shipCorp))
                } else {
                    condition = false
                }
            }
        }
        return condition
    }

    /** function called from Main, parser the garbage and passes them to validators */
    fun parseGarbage(garbageJSON: String): Boolean {
        try {
            val garbage = JSONObject(File(garbageJSON).readText()).getJSONArray("garbage")
            return createGarbage(garbage)
        } catch (e: IOException) {
            logger.error { e }
        } catch (error: FileNotFoundException) {
            logger.error { error }
        }
        return false
    }
    private fun createGarbage(garbage: JSONArray): Boolean {
        for (gar in garbage) {
            if (!validateGarbage(gar as JSONObject)) {
                return false
            }
        }
        return true
    }

    private fun validateGarbage(garbage: JSONObject): Boolean {
        val uniqueId: Boolean = accumulator.garbage[garbage.getInt(id)] == null
        val location: Tile? = accumulator.getTileById(garbage.getInt(location))
        val locationExists: Boolean = location != null
        var locationIsNotLand: Boolean = location is Shore || location is ShallowOcean
        if (garbage.getString(type) != "CHEMICALS") {
            locationIsNotLand = locationIsNotLand || location is DeepOcean
        }
        if (uniqueId && locationExists && locationIsNotLand) {
            return createGarbageObj(garbage)
        }
        return false
    }
    private fun createGarbageObj(garbage: JSONObject): Boolean {
        val garbageId: Int = garbage.getInt(id)
        val garbageType: GarbageType = GarbageType.valueOf(garbage.getString(type))
        val garbageLocation: Tile = accumulator.getTileById(garbage.getInt(location)) ?: return false
        val amount: Int = garbage.getInt("amount")
        val g = Garbage(garbageId, amount, garbageType, mutableSetOf())
        accumulator.addGarbage(garbageId, g)
        garbageLocation.addGarbage(g)
        return true
    }
}
