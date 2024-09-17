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

class Accumulator {
    var map: Sea? = null
    var corporations: MutableMap<Int, Corporation> = mutableMapOf()
    private var ships: MutableMap<Int, Ship> = mutableMapOf()
    var events: MutableMap<Int, Event> = mutableMapOf()
    private var garbage: MutableMap<Int, Garbage> = mutableMapOf()
    private var tasks: MutableMap<Int, Task> = mutableMapOf()
    private var rewards: MutableMap<Int, Reward> = mutableMapOf()
    private var mapCorporationToHarbor: Map<Int, MutableList<Tile>> = mutableMapOf()
    private var mapCorporationToShips: Map<Int, MutableList<Int>> = mutableMapOf()
    var tiles: MutableMap<Int, Tile> = mutableMapOf()
    var tilesByCoordinate: MutableMap<Vec2D, Tile> = mutableMapOf()


    public fun getHarborsOfCorporation(corporationId: Int): List<Tile>? {
        return mapCorporationToHarbor[corporationId]
    }

    public fun getShipsById(shipId: Int): Ship? {
        return ships[shipId]
    }

    public fun getTileById(tileId: Int): Tile? {
        return map?.getTileById(tileId)
    }

    public fun getRewardById(rewardId: Int): Reward? {
        return rewards[rewardId]
    }

    public fun addReward(rewardId: Int, reward: Reward) {
        rewards[rewardId] = reward
    }

    public fun addTask(taskId: Int, task: Task) {
        tasks[taskId] = task
    }

    public fun getTaskById(taskId: Int): Task? {
        return tasks[taskId]
    }

    public fun getTileByCoordinate(tileCoordinates: Vec2D): Tile? {
        return map?.getTileByPos(tileCoordinates)
    }

    public fun addTile(id: Int, t: Tile) {
        tiles[id] = t
    }

    public fun addTileByCoordinates(coord: Vec2D, t: Tile) {
        tilesByCoordinate[coord] = t
    }

    public fun addShip(shipId: Int, ship: Ship) {
        ships[shipId] = ship
    }

    public fun addShipToCorp(corporationId: Int, shipId: Int) {
        mapCorporationToShips[corporationId]?.add(shipId)
    }

    public fun getShipsOfCorp(corpId: Int): List<Int>? {
        return mapCorporationToShips[corpId]
    }

    public fun addEvent(eventId: Int, event: Event) {
        events[eventId] = event
    }

    public fun getEventById(eventId: Int): Event? {
        return events[eventId]
    }

    public fun addCorporation(corpId: Int, corporation: Corporation) {
        corporations[corpId] = corporation
    }

    public fun addGarbage(garbageId: Int, garbage: Garbage) {
        this.garbage[garbageId] = garbage
    }

    public fun getGarbageById(garbageId: Int): Garbage? {
        return garbage[garbageId]
    }


}
