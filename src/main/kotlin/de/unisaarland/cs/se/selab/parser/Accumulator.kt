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
    private var map: Sea? = null
    private var corporations: MutableMap<Int, Corporation> = mutableMapOf()
    private var ships : MutableMap<Int, MutableList<Ship>> = mutableMapOf()
    private var events: MutableMap<Int, Event> = mutableMapOf()
    private var garbage: MutableMap<Int, Garbage> = mutableMapOf()
    private var tasks: MutableMap<Int, Task> = mutableMapOf()
    private var rewards: MutableMap<Int, Reward> = mutableMapOf()
    private var mapCorporationToHarbor: Map<Int, MutableList<Tile>> = mutableMapOf()
    private var mapCorporationToShips: Map<Int, MutableList<Int>> = mutableMapOf()

    public fun getHarborsOfCorporation(corporationId: Int): List<Tile>? {
        return mapCorporationToHarbor[corporationId]
    }
    public fun getShipsIDofCorporation(corporationId: Int): List<Int>{
        return ships[corporationId].map { it.id }
    }
    public fun getTileFromMap(tileId: Int): Tile? {
        return map?.getTileById(tileId)
    }
    public fun getRewardById(rewardId: Int): Reward? {
        return rewards[rewardId]
    }
    public fun addTask(taskId: Int, task: Task): Unit {
        tasks[taskId] = task
    }
    public fun getTaskById(taskId: Int): Task? {
        return tasks[taskId]
    }
    public fun getTileFromMap(tileCoordinates: Vec2D): Tile {
        return map?.getTileByPos(tileCoordinates)!!
    }
    public fun addTile(id: Int, t: Tile): Unit {

    }
    public fun addTileByCoordinates(coord: Vec2D, t: Tile): Unit {}
    public fun addShip(shipId: Int, ship: Ship) {
    }
    public fun addShipToCorp(corporationId: Int, shipId: Int) {
        mapCorporationToShips[corporationId]?.add(shipId)
    }
    public fun getShipsOfCorp(corpId: Int): List<Int>? {
        return mapCorporationToShips[corpId]
    }
    public fun addEvent(eventId: Int, event: Event) {

    }
    public fun addCorporation(corpId: Int, corporation: Corporation) {
        corporations[corpId] = corporation
    }
    public fun addGarbage(garbageId: Int, garbage: Garbage){
        garbage[garbageId] = garbage
    }


}
