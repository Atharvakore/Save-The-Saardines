package de.unisaarland.cs.se.selab.corporation
import de.unisaarland.cs.se.selab.ships.CollectingShip
import de.unisaarland.cs.se.selab.tiles.Tile
import de.unisaarland.cs.se.selab.tiles.Vec2D

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

        // ships can cooperate if they have the same position (are on the same tile)
        // if

        val myCoordinatingShips : List<Ship> = filterCoordinatingShips()

        myCoordinatingShips.forEach {
            val otherShip: Ship? = ships.find { it.position == it.position }
            if (otherShip != null) {
                for(ship in otherShip.getOwner().ownedShips) {
                    if(ship.capabilities.find { it is CoordinatingShip }){
                        TODO("implementation remains")
                    }
                }
            }

        }


    }

    public fun run(sea: Sea, otherShips: List<Ship>): Unit {

        moveShips(sea)
        collectGarbage(sea)
        refuelAndUnloadShips(sea)
    }

    public fun getActiveTasks(): List<Task> {

        return tasks.filter { it.checkCondition() }

    }

    private fun moveShips(sea: Sea): Unit {

        TODO("yet to implement")
    }

    private fun collectGarbage(sea: Sea): Unit {

        TODO("yet to implement")
    }

    private fun refuelAndUnloadShips(sea: Sea): Unit {

        val shipsOnHarbor: List<Ship> = getShipsOnHarbor()
        if(shipsOnHarbor.isNotEmpty()){
            for(ship in shipsOnHarbor){
                if(ship.capabilities.find { it is CollectingShip }){
                    ship.unload()
                }
                ship.refuel()
            }
        }
    }

    private fun getShipsOnHarbor(): List<Ship> {

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