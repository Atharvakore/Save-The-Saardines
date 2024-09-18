package corporation

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.ships.ShipCapability
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.ShallowOcean
import de.unisaarland.cs.se.selab.tiles.Shore
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D

class UTFactory {

    fun createListOfCorporations(
        numOfCorp: Int,
        ownedShips: Map<Int, MutableList<Ship>>,
        ownedHarbors: Map<Int, List<Shore>>,
        acceptedGarbageType: Map<Int, List<GarbageType>>,
        tasks: Map<Int, List<Task>>
    ): List<Corporation> {
        return (0 until numOfCorp).map { id ->
            val ships = ownedShips[id] ?: mutableListOf()
            val harbors = ownedHarbors[id] ?: emptyList()
            val acceptedGarbage = acceptedGarbageType[id] ?: emptyList()
            val corpTasks = tasks[id] ?: emptyList()
            Corporation(id, "unknown$id", ships, harbors, acceptedGarbage, corpTasks)
        }
    }

    fun createListOfShip(
        numOfShips: Int,
        capabilities: Map<Int, MutableList<ShipCapability>>,
        position: Map<Int, Tile>,
        owner: Map<Int, Corporation>
    ): List<Ship> {
        return (0 until numOfShips).map { id ->
            val velocity = 50
            val accel = 10
            val fuelCap = 5000
            val fuelConsume = 7
            val capability = capabilities[id] ?: mutableListOf()
            val ship = Ship(
                id = id,
                maxVelocity = velocity,
                acceleration = accel,
                fuelCapacity = fuelCap,
                fuelConsumption = fuelConsume,
                capabilities = capability
            )
            ship.position = position[id] ?: Shore(0, Vec2D(-1, -1), listOf(), listOf(), false)
            ship.owner = owner[id] ?: Corporation(-1, "Unknown", mutableListOf(), listOf(), listOf(), listOf())
            ship.name = "Ship$id" // Assign a name to the ship

            ship // Return the ship
        }
    }

    fun createShallowOceanTiles(
        numOfTiles: Int,
        pos: Map<Int, Vec2D>,
        adjacentTiles: Map<Int, List<Tile>>,
        garbage: Map<Int, List<Garbage>>,
    ): List<ShallowOcean> {
        return (0 until numOfTiles).map { id ->
            val position = pos[id] ?: Vec2D(0, 0)
            val adjacent = adjacentTiles[id] ?: emptyList()
            val tileGarbage = garbage[id] ?: emptyList()
            ShallowOcean(
                id,
                position,
                adjacent,
                tileGarbage,
            )
        }
    }

    fun createShore(
        numOfTiles: Int,
        pos: Map<Int, Vec2D>,
        adjacentTiles: Map<Int, List<Tile>>,
        garbage: Map<Int, List<Garbage>>,
        harbor: Map<Int, Boolean>
    ): List<Shore> {
        return (0 until numOfTiles).map { id ->
            val position = pos[id] ?: Vec2D(0, 0)
            val adjacent = adjacentTiles[id] ?: emptyList()
            val tileGarbage = garbage[id] ?: emptyList()
            val tileCurrent = harbor[id] ?: false
            Shore(
                id,
                position,
                adjacent,
                tileGarbage,
                tileCurrent
            )
        }
    }

    fun createDeepOcean(
        numOfTiles: Int,
        pos: Map<Int, Vec2D>,
        adjacentTiles: Map<Int, List<Tile>>,
        garbage: Map<Int, List<Garbage>>,
        current: Map<Int, Current?>
    ): List<DeepOcean> {
        return (0 until numOfTiles).map { id ->
            val position = pos[id] ?: Vec2D(0, 0)
            val adjacent = adjacentTiles[id] ?: emptyList()
            val tileGarbage = garbage[id] ?: emptyList()
            val tileCurrent = current[id]
            DeepOcean(
                id,
                position,
                adjacent,
                tileGarbage,
                tileCurrent
            )
        }
    }

    fun createGarbage(
        numOfPiles: Int,
        amount: Map<Int, Int>,
        type: Map<Int, GarbageType>,
        trackedBy: Map<Int, Set<Corporation>?>,
    ): List<Garbage> {
        return (0 until numOfPiles).map { id ->
            val garbageAmount = amount[id] ?: 0
            val garbageType = type[id] ?: GarbageType.OIL
            val garbageTrackedBy = trackedBy[id]
            Garbage(id, garbageAmount, garbageType, garbageTrackedBy)
        }
    }

    fun createEmptyTasks(num: Int): Map<Int, List<Task>> {
        return (0 until num).associateWith { emptyList() }
    }
}
