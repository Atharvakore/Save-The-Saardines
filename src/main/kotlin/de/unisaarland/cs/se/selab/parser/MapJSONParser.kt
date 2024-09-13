package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.tiles.Tile
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.lang.System.Logger

class MapJSONParser(override val accumulator: Accumulator) : JSONParser {

    public fun parseMap(pathFile: String): Boolean {

        val tiles = createTileObjects(pathFile)
        if (tiles.isEmpty) {
            return false
        }
        return validateTiles(tiles)
        //println(jsonContent);
    }

    private fun createTileObjects(filePath: String): JSONArray {
        try {
            val jsonTiles = JSONObject(File(filePath).readText()).getJSONArray("tiles")
            return jsonTiles
        } catch (error: Exception) {

        }
        return JSONArray()
    }

    private fun validateTiles(objects: JSONArray): Boolean {
        for (elem in objects) {
            if (validateTile(elem as JSONObject)) {
                val tile = this.createTile(elem)
                accumulator.addTile(tile.getId(), tile);
            }
            else{
                return false
            }
        }
        return true
    }

    private fun validateTile(tile: JSONObject): Boolean {

    }

    private fun createTile(tile: JSONObject): Tile {

    }

    private fun createMap(): Sea {

    }
}
