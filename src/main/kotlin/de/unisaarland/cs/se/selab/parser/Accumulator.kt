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
    var map: Sea = Sea
    val corporations: MutableMap<Int, Corporation> = mutableMapOf()
    val ships: MutableMap<Int, Ship> = mutableMapOf()
    val events: MutableMap<Int, Event> = mutableMapOf()
    val garbage: MutableMap<Int, Garbage> = mutableMapOf()
    val tasks: MutableMap<Int, Task> = mutableMapOf()
    val rewards: MutableMap<Int, Reward> = mutableMapOf()
    val mapCorporationToHarbor: Map<Int, MutableList<Tile>> = mutableMapOf()
    val mapCorporationToShips: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    val tiles: MutableMap<Int, Tile> = mutableMapOf()
    val tilesByCoordinate: MutableMap<Vec2D, Tile> = mutableMapOf()
    val listOfHarbors: MutableList<Int> = mutableListOf()

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
        return map.getTileByPos(tileCoordinates)
    }

    /** return tile by position*/
    fun getTileByPos(coordinate: Vec2D): Tile? {
        for ((pos, tile) in tilesByCoordinate) {
            if (pos == coordinate) return tile
        }
        return null
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
