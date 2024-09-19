package corporation

import de.unisaarland.cs.se.selab.corporation.Corporation
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.ships.Container
import de.unisaarland.cs.se.selab.ships.CoordinatingShip
import de.unisaarland.cs.se.selab.ships.ScoutingShip
import de.unisaarland.cs.se.selab.ships.Ship
import de.unisaarland.cs.se.selab.ships.ShipCapability
import de.unisaarland.cs.se.selab.tasks.Task
import de.unisaarland.cs.se.selab.tiles.Current
import de.unisaarland.cs.se.selab.tiles.DeepOcean
import de.unisaarland.cs.se.selab.tiles.Direction
import de.unisaarland.cs.se.selab.tiles.Garbage
import de.unisaarland.cs.se.selab.tiles.GarbageType
import de.unisaarland.cs.se.selab.tiles.Sea
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
        return (1 until numOfCorp).map { id ->
            val ships = ownedShips[id] ?: mutableListOf()
            val harbors = ownedHarbors[id].orEmpty()
            val acceptedGarbage = acceptedGarbageType[id].orEmpty()
            val corpTasks = tasks[id].orEmpty()
            Corporation(id, "unknown$id", ships, harbors, acceptedGarbage, corpTasks)
        }
    }

    fun createListOfShip(
        numOfShips: Int,
        capabilities: Map<Int, MutableList<ShipCapability>>,
        position: Map<Int, Tile>,
        owner: Map<Int, Corporation>
    ): List<Ship> {
        return (1 until numOfShips).map { id ->
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
            ship.position = position[id] ?: Shore(0, Vec2D(-1, -1), emptyList(), emptyList(), false)
            ship.owner = owner[id] ?: Corporation(-1, "Unknown", mutableListOf(), emptyList(), emptyList(), emptyList())
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
        return (1 until numOfTiles).map { id ->
            val position = pos[id] ?: Vec2D(0, 0)
            val adjacent = adjacentTiles[id].orEmpty()
            val tileGarbage = garbage[id].orEmpty()
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
        return (1 until numOfTiles).map { id ->
            val position = pos[id] ?: Vec2D(0, 0)
            val adjacent = adjacentTiles[id].orEmpty()
            val tileGarbage = garbage[id].orEmpty()
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
        return (1 until numOfTiles).map { id ->
            val position = pos[id] ?: Vec2D(0, 0)
            val adjacent = adjacentTiles[id].orEmpty()
            val tileGarbage = garbage[id].orEmpty()
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
        return (1 until numOfPiles).map { id ->
            val garbageAmount = amount[id] ?: 0
            val garbageType = type[id] ?: GarbageType.OIL
            val garbageTrackedBy = trackedBy[id]
            Garbage(id, garbageAmount, garbageType, garbageTrackedBy)
        }
    }

    fun createEmptyTasks(num: Int): Map<Int, List<Task>> {
        return (1 until num).associateWith { emptyList() }
    }

    /**
     * creation of the map provided in the spec
     * not considering adjacentTiles
     * */
    fun createTestingMap() {
        val shoreTiles = createShoreForSea()
        val shallowOceanTiles = listOf(
            ShallowOcean(23, Vec2D(2, 2), emptyList(), emptyList()),
            ShallowOcean(24, Vec2D(3, 2), emptyList(), emptyList()),
            ShallowOcean(25, Vec2D(4, 2), emptyList(), emptyList()),
            ShallowOcean(26, Vec2D(5, 2), emptyList(), emptyList()),
            ShallowOcean(27, Vec2D(6, 2), emptyList(), emptyList()),
            ShallowOcean(28, Vec2D(7, 2), emptyList(), emptyList()),
            ShallowOcean(33, Vec2D(2, 3), emptyList(), emptyList()),
            ShallowOcean(38, Vec2D(7, 3), emptyList(), emptyList()),
            ShallowOcean(43, Vec2D(2, 4), emptyList(), emptyList()),
            ShallowOcean(48, Vec2D(7, 4), emptyList(), emptyList()),
            ShallowOcean(53, Vec2D(2, 5), emptyList(), emptyList()),
            ShallowOcean(58, Vec2D(7, 5), emptyList(), emptyList()),
            ShallowOcean(63, Vec2D(2, 6), emptyList(), emptyList()),
            ShallowOcean(68, Vec2D(7, 6), emptyList(), emptyList()),
            ShallowOcean(73, Vec2D(2, 7), emptyList(), emptyList()),
            ShallowOcean(74, Vec2D(3, 7), emptyList(), emptyList()),
            ShallowOcean(75, Vec2D(4, 7), emptyList(), emptyList()),
            ShallowOcean(76, Vec2D(5, 7), emptyList(), emptyList()),
            ShallowOcean(77, Vec2D(6, 7), emptyList(), emptyList()),
            ShallowOcean(78, Vec2D(7, 7), emptyList(), emptyList())
        )

        val deepOceanTiles = listOf(
            DeepOcean(33, Vec2D(3, 3), emptyList(), emptyList(), current = Current(10, Direction.D60, 1)),
            DeepOcean(34, Vec2D(3, 4), emptyList(), emptyList(), current = Current(10, Direction.D180, 1)),
            DeepOcean(35, Vec2D(3, 5), emptyList(), emptyList(), current = Current(10, Direction.D60, 1)),
            DeepOcean(36, Vec2D(3, 6), emptyList(), emptyList(), current = Current(10, Direction.D0, 1)),
            DeepOcean(43, Vec2D(4, 3), emptyList(), emptyList(), current = Current(10, Direction.D180, 1)),
            DeepOcean(44, Vec2D(4, 6), emptyList(), emptyList(), current = Current(10, Direction.D0, 1)),
            DeepOcean(53, Vec2D(5, 3), emptyList(), emptyList(), current = Current(10, Direction.D180, 1)),
            DeepOcean(56, Vec2D(6, 3), emptyList(), emptyList(), current = Current(10, Direction.D180, 1)),
            DeepOcean(56, Vec2D(6, 4), emptyList(), emptyList(), current = Current(10, Direction.D240, 1)),
            DeepOcean(56, Vec2D(6, 5), emptyList(), emptyList(), current = Current(10, Direction.D300, 1)),
            DeepOcean(56, Vec2D(6, 6), emptyList(), emptyList(), current = Current(10, Direction.D0, 1)),
            DeepOcean(66, Vec2D(5, 6), emptyList(), emptyList(), current = Current(10, Direction.D0, 1))
        )

        for (deepOcean in deepOceanTiles) {
            deepOcean.adjacentTiles = findAdjacentTiles(deepOcean.pos)
        }

        for (shallowOcean in shallowOceanTiles) {
            shallowOcean.adjacentTiles = findAdjacentTiles(shallowOcean.pos)
        }

        Sea.tiles.addAll(shoreTiles)
        Sea.tiles.addAll(shallowOceanTiles)
        Sea.tiles.addAll(deepOceanTiles)
    }

    private fun createShoreForSea(): List<Shore> {
        val shoreTiles = listOf(
            Shore(12, Vec2D(1, 1), emptyList(), emptyList(), harbor = false),
            Shore(13, Vec2D(2, 1), emptyList(), emptyList(), harbor = false),
            Shore(14, Vec2D(3, 1), emptyList(), emptyList(), harbor = true),
            Shore(15, Vec2D(4, 1), emptyList(), emptyList(), harbor = false),
            Shore(16, Vec2D(5, 1), emptyList(), emptyList(), harbor = false),
            Shore(17, Vec2D(6, 1), emptyList(), emptyList(), harbor = false),
            Shore(18, Vec2D(7, 1), emptyList(), emptyList(), harbor = false),
            Shore(19, Vec2D(8, 1), emptyList(), emptyList(), harbor = false),
            Shore(22, Vec2D(1, 2), emptyList(), emptyList(), harbor = false),
            Shore(29, Vec2D(8, 2), emptyList(), emptyList(), harbor = false),
            Shore(32, Vec2D(1, 3), emptyList(), emptyList(), harbor = false),
            Shore(39, Vec2D(8, 3), emptyList(), emptyList(), harbor = false),
            Shore(42, Vec2D(1, 4), emptyList(), emptyList(), harbor = false),
            Shore(49, Vec2D(8, 4), emptyList(), emptyList(), harbor = false),
            Shore(52, Vec2D(1, 5), emptyList(), emptyList(), harbor = true),
            Shore(59, Vec2D(8, 5), emptyList(), emptyList(), harbor = true),
            Shore(62, Vec2D(1, 6), emptyList(), emptyList(), harbor = false),
            Shore(69, Vec2D(8, 6), emptyList(), emptyList(), harbor = false),
            Shore(72, Vec2D(1, 7), emptyList(), emptyList(), harbor = false),
            Shore(79, Vec2D(8, 7), emptyList(), emptyList(), harbor = false),
            Shore(82, Vec2D(1, 8), emptyList(), emptyList(), harbor = false),
            Shore(89, Vec2D(8, 8), emptyList(), emptyList(), harbor = false),
            Shore(83, Vec2D(2, 8), emptyList(), emptyList(), harbor = false),
            Shore(84, Vec2D(3, 8), emptyList(), emptyList(), harbor = false),
            Shore(85, Vec2D(4, 8), emptyList(), emptyList(), harbor = false),
            Shore(86, Vec2D(5, 8), emptyList(), emptyList(), harbor = false),
            Shore(87, Vec2D(6, 8), emptyList(), emptyList(), harbor = false),
            Shore(88, Vec2D(7, 8), emptyList(), emptyList(), harbor = false),
        )
        for (shore in shoreTiles) {
            shore.adjacentTiles = findAdjacentTiles(shore.pos)
        }
        return shoreTiles
    }

    private fun findAdjacentTiles(pos: Vec2D): List<Tile> {
        return listOfNotNull(
            Sea.getTileByPos(Vec2D(pos.posX + 1, pos.posY)), // East
            Sea.getTileByPos(Vec2D(pos.posX - 1, pos.posY)), // West
            Sea.getTileByPos(Vec2D(pos.posX, pos.posY + 1)), // Southeast
            Sea.getTileByPos(Vec2D(pos.posX, pos.posY - 1)), // Northwest
            Sea.getTileByPos(Vec2D(pos.posX + 1, pos.posY - 1)), // Northeast
            Sea.getTileByPos(Vec2D(pos.posX - 1, pos.posY + 1)) // Southwest
        )
    }

    /**
     * will return 5 scouting, 5 coordinating and 6 collecting ships in order from 1 to 16 with maximum possible values
     * collecting ships are: 2 oil with indexes 11, 12 / 2 plastic with indexes 13, 14 and 2 chemicals with indexes 15,
     * 16 after getting them need to assign tiles and owner
     * */
    fun createShips(): MutableList<Ship> {
        val scouting = ScoutingShip(5)
        val collectingOil = CollectingShip(mutableListOf(Container(GarbageType.OIL, 100000)))
        val collectingPlastic = CollectingShip(mutableListOf(Container(GarbageType.PLASTIC, 5000)))
        val collectingChemicals = CollectingShip(mutableListOf(Container(GarbageType.PLASTIC, 10000)))
        val coordinating = CoordinatingShip(1)

        val listOfShips: MutableList<Ship> = mutableListOf()
        for (i in 1..5) {
            val scoutingShip = Ship(i, 100, 25, 10000, 10, mutableListOf(scouting))
            scoutingShip.name = "ShipId$i"
            listOfShips.add(scoutingShip)
        }

        for (i in 6..10) {
            val coordinatingShip = Ship(i, 50, 15, 5000, 7, mutableListOf(coordinating))
            coordinatingShip.name = "ShipId$i"
            listOfShips.add(coordinatingShip)
        }

        for (i in 11..12) {
            val collectingShip = Ship(i, 50, 10, 5000, 9, mutableListOf(collectingOil))
            collectingShip.name = "ShipId$i"
            listOfShips.add(collectingShip)
        }

        for (i in 14..15) {
            val collectingShip = Ship(i, 50, 10, 5000, 9, mutableListOf(collectingPlastic))
            collectingShip.name = "ShipId$i"
            listOfShips.add(collectingShip)
        }

        for (i in 15..16) {
            val collectingShip = Ship(i, 50, 10, 5000, 9, mutableListOf(collectingChemicals))
            collectingShip.name = "ShipId$i"
            listOfShips.add(collectingShip)
        }

        return listOfShips
    }
}
