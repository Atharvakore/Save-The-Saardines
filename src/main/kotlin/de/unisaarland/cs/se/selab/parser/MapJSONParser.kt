package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import io.github.oshai.kotlinlogging.KotlinLogging
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

/** Parser and Validator for extracting data from Map File  **/
class MapJSONParser(override val accumulator: Accumulator) : JSONParser {

    private val logger = KotlinLogging.logger {}

    /** main function which will execute the parsing stuff **/
    fun parseMap(map: String): Boolean {
        val tiles: JSONArray
        try {
            tiles = JSONObject(map).getJSONArray("tiles")
        } catch (error: IOException) {
            logger.error(error) { "Failed to parse map" }
            return false
        }

        if (tiles.isEmpty) {
            return false
        }
        if (validateTiles(tiles) && checkAdjacentTiles()) {
            createMap()
            return true
        } else {
            return false
        }
    }

    /** validating all tiles **/
    private fun validateTiles(objects: JSONArray): Boolean {
        for (elem in objects) {
            if (validateTile(elem as JSONObject) && elem.getString(CATEGORY) != LAND) {
                val tile = this.createTile(elem)
                accumulator.addTile(tile.id, tile)
                accumulator.addTileByCoordinates(tile.pos, tile)
            } else {
                return false
            }
        }
        return true
    }

    /** validating specific tile **/
    private fun validateTile(tile: JSONObject): Boolean {
        var validated = true
        val id: Int = tile.optInt(ID, -1)
        val coordinates = tile.optJSONObject(COORDINATES, null)
        if (coordinates == null) validated = false
        val x: Int?
        val y: Int?
        if (coordinates.has(X)) {
            x = coordinates.getInt("x")
        } else {
            x = null
            validated = false
        }
        if (coordinates.has(Y)) {
            y = coordinates.getInt(Y)
        } else {
            y = null
            validated = false
        }
        if (x == null || y == null) return false

        validated = validated && checkTileIdAndCoordinates(id, x, y)
        validated = validated && validateTileCategory(tile)

        return validated
    }

    /** Validate the category of tile **/
    private fun validateTileCategory(tile: JSONObject): Boolean {
        var validated = true
        val category = tile.optString(CATEGORY, null) ?: return false
        if (!Companion.category.contains(category)) validated = false
        when (category) {
            SHORE -> {
                if (Companion.requiredForCurrent.any { tile.has(it) }) {
                    validated = false
                }
                val harbor = tile.opt(HARBOR)
                if (harbor !is Boolean) {
                    validated = false
                }
            }

            DEEP_OCEAN -> {
                validated = validated && validateDeepOcean(tile)
            }

            else -> {
                if (tile.has(HARBOR)) return false
                if (Companion.requiredForCurrent.any { tile.has(it) }) {
                    validated = false
                }
            }
        }
        return validated
    }

    private fun validateDeepOcean(tile: JSONObject): Boolean {
        if (tile.has(HARBOR)) return false
        val current = tile.getBoolean(CURRENT)
        if (current) {
            if (requiredForCurrent.all { tile.has(it) }) {
                this.validateCurrent(tile)
            } else {
                return false
            }
        } else if (requiredForCurrent.none { tile.has(it) }) {
            return true
        }
        return false
    }

    /** Validate the id and coordinates of a tile **/
    private fun checkTileIdAndCoordinates(id: Int, x: Int, y: Int): Boolean {
        if (id < 0) return false
        if (accumulator.getTileById(id) != null) return false

        val coord = Vec2D(x, y)
        if (accumulator.getTileByCoordinate(coord) != null) return false

        return true
    }

    private fun validateCurrent(tile: JSONObject): Boolean {
        val intensity = tile.getInt(INTENSITY)
        val speed = tile.getInt(SPEED)
        val direction = tile.getInt(DIRECTION)
        if (direction < 0 || direction > DIRECTION300 || direction % DIRECTION60 != 0) return false
        if (speed < 0 || speed > MAX_SPEED) return false
        if (intensity < 0 || intensity > MAX_INTENSITY) return false
        return true
    }

    private fun getDirection(direction: Int): Direction {
        return when (direction) {
            DIRECTION0 -> Direction.D0
            DIRECTION60 -> Direction.D60
            DIRECTION120 -> Direction.D120
            DIRECTION180 -> Direction.D180
            DIRECTION240 -> Direction.D240
            else -> Direction.D300
        }
    }

    /** Create a tile **/
    private fun createTile(tile: JSONObject): Tile {
        val id = tile.getInt(ID)
        val coordinateX = tile.getJSONObject(COORDINATES).getInt(X)
        val coordinateY = tile.getJSONObject(COORDINATES).getInt(Y)
        val coordinates = Vec2D(coordinateX, coordinateY)
        val category = tile.getString(CATEGORY)
        when (category) {
            DEEP_OCEAN -> {
                val current = tile.getBoolean(CURRENT)
                if (current) {
                    val direction = tile.getInt(DIRECTION)
                    val speed = tile.getInt(SPEED)
                    val intensity = tile.getInt(INTENSITY)
                    val currentObject = Current(speed, getDirection(direction), intensity)
                    return DeepOcean(id, coordinates, listOf(), listOf(), currentObject)
                }
                return DeepOcean(id, coordinates, listOf(), listOf(), null)
            }

            SHALLOW_OCEAN -> {
                return ShallowOcean(id, coordinates, listOf(), listOf())
            }

            SHORE -> {
                val harbor = tile.getBoolean(HARBOR)
                return Shore(id, coordinates, listOf(), listOf(), harbor)
            }

            else -> {
                throw IllegalArgumentException("There should be an Ocean tile")
            }
        }
    }

    /** Create Map based on the information from Accumulator **/
    private fun createMap() {
        for (element in accumulator.tiles.values) {
            Sea.tiles.add(element)
        }
    }

    /** Check if the tiles has correct neighbours; For now Land is Empty tile*/
    private fun checkAdjacentTiles(): Boolean {
        accumulator.tiles.forEach {
            val tile = it.value
            val x = tile.pos.posX
            val y = tile.pos.posY
            var correct = true
            val adjacentTile0 = accumulator.getTileByCoordinate(Vec2D(x - 1, y))
            val adjacentTile60 = accumulator.getTileByCoordinate(Vec2D(x - 1, y - 1))
            val adjacentTile120 = accumulator.getTileByCoordinate(Vec2D(x - 1, y + 1))
            val adjacentTile180 = accumulator.getTileByCoordinate(Vec2D(x + 1, y))
            val adjacentTile240 = accumulator.getTileByCoordinate(Vec2D(x + 1, y + 1))
            val adjacentTile300 = accumulator.getTileByCoordinate(Vec2D(x - 1, y + 1))
            val adjacentTiles = listOf(
                adjacentTile0,
                adjacentTile60,
                adjacentTile120,
                adjacentTile180,
                adjacentTile240,
                adjacentTile300
            )
            when (tile) {
                is Shore -> {
                    correct =
                        correct && adjacentTiles.all { it == null || it is Shore || it is ShallowOcean }
                }

                is DeepOcean -> {
                    correct =
                        correct && adjacentTiles.all { it == null || it is DeepOcean || it is ShallowOcean }
                }

                is ShallowOcean -> {
                    correct =
                        correct && adjacentTiles.all {
                            it == null || it is Shore || it is ShallowOcean || it is DeepOcean
                        }
                }

                else -> {
                    correct = true
                }
            }
            if (!correct) return false
        }

        return true
    }

    /** String/integer constants for the map parser. */
    companion object Companion {
        const val CURRENT = "current"
        const val ID = "id"
        const val COORDINATES = "coordinates"
        const val CATEGORY = "category"
        const val HARBOR = "harbor"
        const val DIRECTION = "direction"
        const val INTENSITY = "intensity"
        const val SPEED = "speed"
        const val X = "x"
        const val Y = "y"
        const val DEEP_OCEAN = "DEEP_OCEAN"
        const val SHALLOW_OCEAN = "SHALLOW_OCEAN"
        const val LAND = "LAND"
        const val SHORE = "SHORE"
        const val DIRECTION0 = 0
        const val DIRECTION60 = 60
        const val DIRECTION120 = 120
        const val DIRECTION180 = 180
        const val DIRECTION240 = 240
        const val DIRECTION300 = 300
        const val MAX_SPEED = 30
        const val MAX_INTENSITY = 10
        val category: Array<String> = arrayOf(LAND, SHORE, SHALLOW_OCEAN, DEEP_OCEAN)
        val requiredForCurrent: Array<String> = arrayOf(CURRENT, DIRECTION, INTENSITY, SPEED)
    }
}
