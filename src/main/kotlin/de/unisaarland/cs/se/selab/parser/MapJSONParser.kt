package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.lang.System.Logger

class MapJSONParser(override val accumulator: Accumulator) : JSONParser {

    /** **/
    public fun parseMap(pathFile: String): Boolean {

        val tiles = createTileObjects(pathFile)
        if (tiles.isEmpty) {
            return false
        }
        return validateTiles(tiles)
    }

    /** **/
    private fun createTileObjects(filePath: String): JSONArray {
        try {
            val jsonTiles = JSONObject(File(filePath).readText()).getJSONArray("tiles")
            return jsonTiles
        } catch (error: Exception) {

        }
        return JSONArray()
    }

    /** **/
    private fun validateTiles(objects: JSONArray): Boolean {
        for (elem in objects) {
            if (validateTile(elem as JSONObject)) {
                val tile = this.createTile(elem)
                accumulator.addTile(tile.getId(), tile);
                accumulator.addTileByCoordinates(tile.getCoordinates(), tile);
            } else {
                return false
            }
        }
        return true
    }

    /** **/
    private fun validateTile(tile: JSONObject): Boolean {

    }

    private fun createTile(tile: JSONObject): Tile {
        val id = tile.getString("id")
        val coordinate_x = tile.getJSONObject("coordinates").getInt("x")
        val coordinate_y = tile.getJSONObject("coordinates").getInt("y")
        val coordinates = Vec2D(coordinate_x, coordinate_y)
        val category = tile.getString("category")
        when (category) {
            "DEEP_OCEAN" -> {
                val current = tile.getBoolean("current")
                return Tile()

            }

            "SHALLOW_OCEAN" -> {
                val harbor = tile.getBoolean("harbor")
                return Tile()
            }

            else -> {
                return Tile()
            }
        }
    }

    private fun createMap(): Sea {

    }
}
