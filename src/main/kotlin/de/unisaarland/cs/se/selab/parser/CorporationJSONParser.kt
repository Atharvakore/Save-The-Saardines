package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.logger.Logger
import org.json.JSONObject

class CorporationJSONParser(
    override val accumulator: Accumulator,
) : JSONParser {

    /** Parsing function **/
    public fun parseCorporations(filePath: String): Boolean {
        return false;
    }

    public fun parseShips(filePath: String): Boolean {
        return false;
    }

    private fun createCorporationObjects(filePath: String): List<JSONObject> {
        //TODO
    }

    private fun validateCorporations(corpObjects: List<JSONObject>): Boolean {
        return false;
    }

    private fun validateCorporation(corporation: JSONObject): Boolean {
        return false;
    }

    private fun createShipObjects(filePath: String): List<JSONObject> {

    }

    private fun createShip(ship: JSONObject): Ship {

    }

    private fun createCorporation(corporation: JSONObject): Corporation {

    }

    private fun validateShip(shipObject: JSONObject): Boolean {
        return false;
    }

    private fun validateShips(corporationObjects: List<JSONObject>): Boolean {
        return false;
    }
}