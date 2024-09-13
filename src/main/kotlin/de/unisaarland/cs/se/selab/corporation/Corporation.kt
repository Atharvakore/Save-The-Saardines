package de.unisaarland.cs.se.selab.corporation
import de.unisaarland.cs.se.selab.tiles.Tile

class Corporation(
    val id: Int,
    val name: String,
    val ownedShips: List<Ship>,
    val ownedHarbors: List<Shore>,
    val acceptedGarbageType: List<GarbageType>,
    val tasks: List<Task>
) {
    var trackedGarbage: MutableList<Garbage> = mutableListOf()
    var partnerGarbage: MutableMap<Int, Vec2D> = mutableMapOf()
    var lastCoordinatingCorporation: Corporation? = null


    public fun cooperate(ships: List<Ship>): Corporation {
        TODO("yet to implement")
    }

    public fun cooperateWith(ships : List<Ship>) {

        TODO("yet to implement")

    }

    public fun run(sea: Sea, otherShips: List<Ship>): Unit {

        TODO("yet to implement")
    }

    public fun getActiveTasks(): List<Task> {

        TODO("yet to implement")
    }

    private fun moveShips(sea: Sea): Unit {

        TODO("yet to implement")
    }

    private fun collectGarbage(sea: Sea): Unit {

        TODO("yet to implement")
    }

    private fun refuelAndUnloadShips(sea: Sea): Unit {

        TODO("yet to implement")
    }

    private fun getShipsOnHarbor(): Boolean {

        TODO("yet to implement")
    }

    private fun findClosestShip(sourceMap: Map<Tile, List<Tile>>, ships: List<Ship>) {

        TODO("yet to implement")
    }

    private fun findClosestHarbor(sourceMap: Map<Tile, List<Tile>>, harbors: List<Shore>) {


        TODO("yet to implement")
    }

    private fun filterCollectingShip(): List<Ship> {
        TODO("yet to implement")
    }

    private fun filterScoutingShips(): List<Ship> {

        TODO("yet to implement")
    }

    private fun filterCoordinatingShips(): List<Ship> {

        TODO("yet to implement")
    }

}