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
        return if (!tiles.isEmpty && validateTiles(tiles) && checkAdjacentTiles()) {
            createMap()
            true
        } else {
            false
        }
    }

    /** validating all tiles **/
    private fun validateTiles(objects: JSONArray): Boolean {
        for (elem in objects) {
            if (validateTile((elem ?: error("There should be a tile in ValidateTiles")) as JSONObject)) {
                val element = elem as JSONObject
                if (element.getString(CATEGORY) != LAND) {
                    val tile = this.createTile(element)
                    accumulator.addTile(tile.id, tile)
                    accumulator.addTileByCoordinates(tile.pos, tile)
                }
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
        validated = validated && checkAdditionalProperties(tile)
        validated = validated && checkTileIdAndCoordinates(id, x, y)
        validated = validated && validateTileCategory(tile)

        return validated
    }

    private fun checkAdditionalProperties(tile: JSONObject): Boolean {
        val category: String = tile.getString(CATEGORY)
        var valid: Boolean
        if (category == LAND || category == SHALLOW_OCEAN) {
            valid = !tile.has(CURRENT) && !tile.has(SPEED) && !tile.has(DIRECTION) && !tile.has(INTENSITY) &&
                    !tile.has(HARBOR)
        } else if (category == SHORE) {
            valid = !tile.has(CURRENT) && !tile.has(SPEED) && !tile.has(DIRECTION) && !tile.has(INTENSITY)
        } else {
            valid = checkDeepOceanAdditional(tile)
        }
        return valid
    }

    private fun checkDeepOceanAdditional(tile: JSONObject): Boolean {
        val current: Boolean = tile.getBoolean(CURRENT)
        var valid: Boolean = !tile.has(HARBOR)
        if (!current) {
            valid = valid && !tile.has(SPEED) && !tile.has(DIRECTION) && !tile.has(INTENSITY)
        }
        return valid
    }

    /** Validate the category of tile **/
    private fun validateTileCategory(tile: JSONObject): Boolean {
        var validated = true
        val category = tile.optString(CATEGORY, null) ?: return false
        if (!Companion.category.contains(category)) validated = false
        when (category) {
            SHORE -> {
                if (requiredForCurrent.any { tile.has(it) }) {
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
                if (tile.has(HARBOR) || requiredForCurrent.any { tile.has(it) }) {
                    validated = false
                }
            }
        }
        return validated
    }

    private fun validateDeepOcean(tile: JSONObject): Boolean {
        var condition = true
        if (tile.has(HARBOR)) condition = false
        val current = tile.getBoolean(CURRENT)
        if (!condition && current) {
            return if (requiredForCurrent.all { tile.has(it) }) {
                validateCurrent(tile)
            } else {
                false
            }
        } else if (requiredForCurrent.none { tile.has(it) }) {
            condition = true
        }
        return condition
    }

    /** Validate the id and coordinates of a tile **/
    private fun checkTileIdAndCoordinates(id: Int, x: Int, y: Int): Boolean {
        if (id < 0 || accumulator.tiles[id] != null) return false
        val coord = Vec2D(x, y)
        return accumulator.getTileByCoordinate(coord) == null
    }

    private fun validateCurrent(tile: JSONObject): Boolean {
        val intensity = tile.getInt(INTENSITY)
        val speed = tile.getInt(SPEED)
        val direction = tile.getInt(DIRECTION)
        var condition = direction < 0 || direction > DIRECTION300 || direction % DIRECTION60 != 0
        condition = condition || speed < 0 || speed > MAX_SPEED
        return !(condition || intensity < 0 || intensity > MAX_INTENSITY)
    }

    /** Create a tile **/
    private fun createTile(tile: JSONObject): Tile {
        val id = tile.getInt(ID)
        val coordinateX = tile.getJSONObject(COORDINATES).getInt(X)
        val coordinateY = tile.getJSONObject(COORDINATES).getInt(Y)
        val coordinates = Vec2D(coordinateX, coordinateY)
        val category = tile.getString(CATEGORY)
        val result = when (category) {
            DEEP_OCEAN -> {
                val current = tile.getBoolean(CURRENT)
                var currentObject: Current? = null
                if (current) {
                    val direction = tile.getInt(DIRECTION)
                    val speed = tile.getInt(SPEED)
                    val intensity = tile.getInt(INTENSITY)
                    currentObject = Current(speed, requireNotNull(Direction.getDirection(direction)), intensity)
                }
                DeepOcean(id, coordinates, emptyList(), emptyList(), currentObject)
            }

            SHALLOW_OCEAN -> {
                ShallowOcean(id, coordinates, emptyList(), emptyList())
            }

            SHORE -> {
                val harbor = tile.getBoolean(HARBOR)
                Shore(id, coordinates, emptyList(), emptyList(), harbor)
            }

            else -> {
                throw IllegalArgumentException("There should be an Ocean tile")
            }
        }
        return result
    }

    /** Create Map based on the information from Accumulator **/
    private fun createMap() {
        for (element in accumulator.tiles.values) {

            val x = element.pos.posX
            val y = element.pos.posY
            val nextValue = if (x % 2 == 0 && y % 2 == 0) {
                1
            } else {
                0
            }
            val adjacentTile0 = accumulator.getTileByCoordinate(Vec2D(x - 1, y)) // west
            val adjacentTile60 = accumulator.getTileByCoordinate(Vec2D(x - 1 + nextValue, y - 1))
            val adjacentTile120 = accumulator.getTileByCoordinate(Vec2D(x + nextValue, y - 1))
            val adjacentTile180 = accumulator.getTileByCoordinate(Vec2D(x + 1, y)) // east
            val adjacentTile240 = accumulator.getTileByCoordinate(Vec2D(x + 1 + nextValue, y + 1))
            val adjacentTile300 = accumulator.getTileByCoordinate(Vec2D(x + nextValue, y + 1))
            val adjacentTiles = listOf(
                adjacentTile0,
                adjacentTile60,
                adjacentTile120,
                adjacentTile180,
                adjacentTile240,
                adjacentTile300
            )
            element.adjacentTiles = adjacentTiles
            Sea.tiles.add(element)
        }
    }

    /** Check if the tiles has correct neighbours; For now Land is Empty tile*/
    private fun checkAdjacentTiles(): Boolean {
        accumulator.tiles.forEach {
            val tile = it.value
            val x = tile.pos.posX
            val y = tile.pos.posY
            val correct: Boolean
            val nextValue = if (x % 2 == 0 && y % 2 == 0) {
                1
            } else {
                0
            }
            val adjacentTile0 = accumulator.getTileByCoordinate(Vec2D(x - 1, y)) // west
            val adjacentTile60 = accumulator.getTileByCoordinate(Vec2D(x - 1 + nextValue, y - 1))
            val adjacentTile120 = accumulator.getTileByCoordinate(Vec2D(x + nextValue, y - 1))
            val adjacentTile180 = accumulator.getTileByCoordinate(Vec2D(x + 1, y)) // east
            val adjacentTile240 = accumulator.getTileByCoordinate(Vec2D(x + 1 + nextValue, y + 1))
            val adjacentTile300 = accumulator.getTileByCoordinate(Vec2D(x + nextValue, y + 1))
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
                        adjacentTiles.all { t -> t == null || t is Shore || t is ShallowOcean }
                }

                is DeepOcean -> {
                    correct =
                        adjacentTiles.all { t -> t == null || t is DeepOcean || t is ShallowOcean }
                }

                is ShallowOcean -> {
                    correct =
                        adjacentTiles.all { t ->
                            t == null || t is Shore || t is ShallowOcean || t is DeepOcean
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

        // const val DIRECTION0 = 0
        const val DIRECTION60 = 60

        /* const val DIRECTION120 = 120
        const val DIRECTION180 = 180
        const val DIRECTION240 = 240 */
        const val DIRECTION300 = 300
        const val MAX_SPEED = 30
        const val MAX_INTENSITY = 10
        val category: Array<String> = arrayOf(LAND, SHORE, SHALLOW_OCEAN, DEEP_OCEAN)
        val requiredForCurrent: Array<String> = arrayOf(DIRECTION, INTENSITY, SPEED)
    }
}
