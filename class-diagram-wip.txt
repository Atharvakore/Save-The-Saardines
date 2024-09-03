import java.util.*

enum class Direction { D0, D60, D120, D180, D240, D300 }
enum class GarbageType { OIL, PLASTIC, CHEMICALS }
class Vec2D {
    var x: Int = 0
    var y: Int = 0
    constructor(x: Int, y: Int) {
        this.x = x
        this.y = y
    }
    constructor() { }
}
class Corporation {
    var id: Int = 0
    var name: String = ""
    var acceptedGarbageTypes: Array<GarbageType> = arrayOf()
    var ownedShips: Array<Ship> = arrayOf()
    var ownedHarbors: Array<Harbor> = arrayOf()
}
class Scenario {
    var events: Array<Event> = arrayOf()
    var garbage: Array<Garbage> = arrayOf()
    var ships: Array<Ship> = arrayOf()
}

interface JSONParser<T> {
    fun parse(json: String): T
}
class MapJSONParser : JSONParser<Tile> {
    override fun parse(json: String): Tile {
        throw UnsupportedOperationException()
    }
}
class ScenarioJSONParser : JSONParser<Scenario> {
    override fun parse(json: String): Scenario {
        throw UnsupportedOperationException()
    }
}
class CorporationJSONParser : JSONParser<Corporation> {
    override fun parse(json: String): Corporation {
        throw UnsupportedOperationException()
    }
}

class Garbage {
    var id: Int = 0
    var amount: Int = 0
    var type: GarbageType? = null
    var pos: Vec2D? = null
}

abstract class Tile {
    var pos: Vec2D? = null
    var id: Int = 0
};

class Harbor {
    var owner: Corporation? = null
}

class Current {
    var dir: Direction? = null
    var speed: Int = 0
    var intensity: Int = 0
}

class Land : Tile() { }

abstract class WaterTile : Tile() {
}

class Shore : WaterTile() {
    var harbor: Optional<Harbor> = Optional.empty()
}

class ShallowWater : WaterTile() { }

class DeepWater : WaterTile() {
    var current: Current? = null
}

abstract class Ship {
    var id: Int = 0
    var name: String? = null
    var owner: Corporation? = null
    var pos: Vec2D? = null
    var dir: Direction? = null
    var maxVelocity: Int = 0
    var acceleration: Int = 0
    var fuelCapacity: Int = 0
    var fuelConsumption: Int = 0
    var visibilityRange: Int = 0
}

class ScoutingShip : Ship()
class CoordinatingShip : Ship()
class CollectingShip : Ship() {
    var garbageType: GarbageType? = null
    var garbageCapacity: Int = 0
}

enum class EventType {
    STORM, RESTRICTION, OIL_SPILL
}

abstract class Event {
    var id: Int = 0
    var fireTick: Int = 0
}

abstract class LocalEvent : Event() {
    var pos: Vec2D? = null
    var radius: Int = 0
}

class Storm : LocalEvent() {
    var speed: Int = 0
    var dir: Direction? = null
}

class Restriction : LocalEvent() {
    var duration: Int = 0
}

class OilSpill : LocalEvent() {
    var amount: Int = 0
}

class PirateAttack : Event() {
    var shipId: Int = 0
}

class Simulation {
    var scenario: Scenario? = null
    var corporations: Array<Corporation> = arrayOf()
    var tick: Int = 0
    var maxTicks: Int = 0
    var map: Tile? = null // Graph is strongly connected.

    fun start() {
        throw UnsupportedOperationException()
    }

    fun tick() {
        throw UnsupportedOperationException()
    }
}

// Utils
class Dijkstra {}

class Logger {}

class CLIParser {}

