package de.unisaarland.cs.se.selab.parser

import de.unisaarland.cs.se.selab.corporation.Corporation

class Accumulator {
    private var map: Sea = null
    private var corporations: Map<Int, Corporation> = mutableMapOf()
    private var ships : List<Ship> = mutableListOf()
    private var events: Map<Int, Event> = mutableMapOf()
    private var garbage: Map<Int, Garbage> = mutableMapOf()
    private var Tasks: Map<Int, Task> = mutableMapOf()
    private var rewards: Map<Int, Reward> = mutableMapOf()
    private var mapCorporationToHarbor: Map<Int, List<Tile>> = mutableMapOf()
    private var mapCorporationToShips: Map<Int, List<Int>> = mutableMapOf()


}
