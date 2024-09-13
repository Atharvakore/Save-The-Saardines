package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.tiles.Tile
import org.json.JSONObject

class MapJSONParser(override val accumulator: Accumulator) : JSONParser {

    public fun parseMap(pathFile: String): Boolean {
        return false;
    }

    private fun createTileObjects(filePath: String): List<JSONObject> {

    }

    private fun validateTiles(objects: List<JSONObject>): Boolean {
        return objects.isNotEmpty()
    }

    private fun validateTile(tile: JSONObject): Boolean {
        return false;
    }

    private fun createTile(tile: JSONObject): Tile {

    }

    private fun createMap(): Sea {

    }
}
