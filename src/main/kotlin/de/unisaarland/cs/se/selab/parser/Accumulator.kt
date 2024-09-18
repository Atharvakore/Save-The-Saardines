package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.events.Event
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.tasks.Reward
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.Sea
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D

/** The dataclass which will collect all the data from Parsing */
class Accumulator {
    var map: Sea? = null
    var corporations: MutableMap<Int, Corporation> = mutableMapOf()
    var ships: MutableMap<Int, Ship> = mutableMapOf()
    var events: MutableMap<Int, Event> = mutableMapOf()
    var garbage: MutableMap<Int, Garbage> = mutableMapOf()
    var tasks: MutableMap<Int, Task> = mutableMapOf()
    var rewards: MutableMap<Int, Reward> = mutableMapOf()
    var mapCorporationToHarbor: Map<Int, MutableList<Tile>> = mutableMapOf()
    var mapCorporationToShips: Map<Int, MutableList<Int>> = mutableMapOf()
    var tiles: MutableMap<Int, Tile> = mutableMapOf()
    var tilesByCoordinate: MutableMap<Vec2D, Tile> = mutableMapOf()

    /** Getter for tile based on its ID */
    public fun getTileById(tileId: Int): Tile? {
        return tiles[tileId]
    }

    /** Setter for a reward into accumulator*/
    public fun addReward(rewardId: Int, reward: Reward) {
        rewards[rewardId] = reward
    }

    /** Setter for a task into accumulator*/
    public fun addTask(taskId: Int, task: Task) {
        tasks[taskId] = task
    }

    /** Getter for tile based on its Coordinates */
    public fun getTileByCoordinate(tileCoordinates: Vec2D): Tile? {
        return map?.getTileByPos(tileCoordinates)
    }

    /** Setter for a tile into accumulator*/
    public fun addTile(id: Int, t: Tile) {
        tiles[id] = t
    }

    /** Setter for a tile into accumulator*/
    public fun addTileByCoordinates(coord: Vec2D, t: Tile) {
        tilesByCoordinate[coord] = t
    }

    /** Setter for a ship into accumulator*/
    public fun addShip(shipId: Int, ship: Ship) {
        ships[shipId] = ship
    }

    /** Setter for a ship into accumulator*/
    public fun addShipToCorp(corporationId: Int, shipId: Int) {
        mapCorporationToShips[corporationId]?.add(shipId)
    }

    /** Setter for a event into accumulator*/
    public fun addEvent(eventId: Int, event: Event) {
        events[eventId] = event
    }

    /** Setter for a corporation into accumulator*/
    public fun addCorporation(corpId: Int, corporation: Corporation) {
        corporations[corpId] = corporation
    }

    /** Setter for garbage based on its ID */
    public fun addGarbage(garbageId: Int, garbage: Garbage) {
        this.garbage[garbageId] = garbage
    }
}
