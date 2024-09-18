package de.unisaarland.cs.se.selab.logger

import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tiles.GarbageType

object LoggerCorporationAction {
    fun log(message: String) {
        Logger.output.write(message)
    }

    /** Log a call of Ship.move(). */
    fun logShipMovement(shipId: Int, speed: Int, tileId: Int) {
        log("Ship Movement: Ship $shipId moved with speed $speed to tile $tileId.")
    }

    /** Logged whenever a ship attaches a tracker to a garbage pile. */
    fun logCorporationAttachedTracker(corporationId: Int, garbageId: Int, shipId: Int) {
        log("Corporation Action: Corporation $corporationId attached tracker to garbage $garbageId with ship $shipId.")
    }

    /** Logged whenever Corporation.collectGarbage() is called. */
    fun logCorporationStartCollectGarbage(corporateId: Int) {
        log("Corporation Action: Corporation $corporateId is starting to collect garbage.")
    }

    /** Logged to signify that a ship has collected garbage. */
    fun logGarbageCollectionByShip(shipId: Int, garbageType: GarbageType, garbageId: Int, amount: Int) {
        fun logGarbageCollectionByShip(ship: Ship, garbageType: GarbageType, garbageId: Int, amount: Int) {
            val shipId: Int = ship.id
            val corporationId: Int = ship.owner.id
            when (garbageType) {
                GarbageType.OIL -> Logger.totalOilCollected += amount
                GarbageType.PLASTIC -> Logger.totalPlasticCollected += amount
                GarbageType.CHEMICALS -> Logger.totalChemicalsCollected += amount
            }
            Logger.map[corporationId] = Logger.map.getOrDefault(corporationId, 0) + amount
            log("Garbage Collection: Ship $shipId collected $amount of garbage $garbageType with $garbageId.")
        }
    }

    /** Log the start of cooperation between corporations. */
    fun logCorporationCooperationStart(corporationId: Int) {
        log("Corporation Action: Corporation $corporationId is starting to cooperate with other corporations.")
    }

    /** Log the cooperation between corporations. */
    fun logCooperationBetweenCorporations(
        corporationId: Int,
        otherCorporationId: Int,
        shipId: Int,
        cooperatedShipId: Int
    ) {
        log(
            "Cooperation: Corporation $corporationId" +
                    " cooperated with corporation" +
                    " $otherCorporationId with ship" +
                    " $shipId to ship $cooperatedShipId."
        )
    }

    /** Logged whenever refueling is started. */
    fun logCorporationRefueling(corporationId: Int) {
        log("Corporation Action: Corporation $corporationId is starting to refuel.")
    }

    /** Logged whenever a ship is refueled. */
    fun logRefuelingShip(shipId: Int, tileId: Int) {
        log("Refueling: Ship $shipId refueled at harbor $tileId.")
    }

    /** Logged whenever a ship is unloaded. */
    fun logUnloadShip(shipId: Int, amount: Int, garbageType: GarbageType, tileId: Int) {
        log("Unload: Ship $shipId unloaded $amount of garbage $garbageType at harbor $tileId.")
    }
}
