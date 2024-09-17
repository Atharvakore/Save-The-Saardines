package de.unisaarland.cs.se.selab.logger

import java.io.PrintWriter

/**
 * A stub of the logger class. TODO: Actually fill this in.
 */
/*class Logger {
    companion object {
        var output: LogOutputStream = BackedLogOutputStream(PrintWriter(System.out))

        fun log(message: String) {
            output.write(message)
        }

        fun logCorporationBetweenCorporations(corporationId : Int, otherCorporationId : Int, shipId :Int, cooperatedShipId: Int) {
            log("Cooperation: Corporation $corporationId cooperated with corporation $otherCorporationId with ship $shipId to ship $cooperatedShipId")
        }


    }
}*/
/**
 * I created separate logger . I have a feeling that whole class should be static and not only methods
 */

object Logger {
    var output: LogOutputStream = BackedLogOutputStream(PrintWriter(System.out))
    fun log(message: String) {
        output.write(message)
    }
    fun  logInitializationInfoSuccess(filename:String) {
        log(" Initialization Info: $filename successfully parsed and validated.")
    }
    fun logInitializationInfoFail(filename:String) {
        log("Initialization Info: $filename is invalid.")
    }
    fun  logSimulationStarted(){
        log("Simulation Info: Simulation started.")
    }
    fun  logSimulationEnded(){
        log("Simulation Info: Simulation ended.")
    }
    fun  logSimulationTick(tick:Int){
        log("Simulation Info: Tick $tick started.")
    }
    fun  logCorporationStartMoveShips(corporationId:Int){
        log("Corporation Action: Corporation $corporationId is starting to move its ships.")
    }
    fun  logShipMovement (shipId:Int,speed:Int,tileId:Int){
        log("Ship Movement: Ship $shipId moved with speed $speed to tile $tileId.")
    }
    fun  logCorporationAttachedTracker(corporationId: Int, garbageId: Int, shipId: Int){
        log("Corporation Action: Corporation $corporationId attached tracker to garbage $garbageId with ship $shipId.")
    }
    fun  logCorporationStartCollectGarbage(corporateId:Int){
        log("Corporation Action: Corporation $corporateId is starting to collect garbage.")
    }
    fun  logGarbageCollectionByShip(shipId:Int,garbageType:GarbageType,garbageId:Int,amount:Int){
         log("Garbage Collection: Ship $shipId collected $amount of garbage $garbageType with $garbageId.")
    }
    fun logCorporationCooperationStart(corporationId:Int){
         log("Corporation Action: Corporation $corporationId is starting to cooperate with other corporations.")
    }
    fun  logCooperationBetweenCorporations(corporationId:Int, otherCorporationId: Int, shipId:Int, cooperatedShipId: Int) {
         log("Cooperation: Corporation $corporationId cooperated with corporation $otherCorporationId with ship $shipId to ship $cooperatedShipId.")
    }
    fun  logCorporationRefueling(corporationId:Int){
        log("Corporation Action: Corporation $corporationId is starting to refuel.")
    }
    fun  logRefuelingShip(shipId:Int, tileId: Int){
        log("Refueling: Ship $shipId refueled at harbor $tileId.")
    }
    fun  logUnloadShip(shipId:Int, amount:Int, garbageType:GarbageType, tileId:Int){
        log("Unload: Ship $shipId unloaded $amount of garbage $garbageType at harbor $tileId.")
    }
    fun  logCorporationFinishedActions(corporationId:Int){
        log("Corporation Action: Corporation $corporationId finished its actions.")
    }
    fun  logCurrentDriftGarbage(garbageType:GarbageType, garbageId:Int, amount:Int, startTileId:Int, endTileId:Int) {
        log("Current Drift: $garbageType $garbageId with amount $amount drifted from tile $startTileId to tile $endTileId.")
    }
    fun  logCurrentDriftShip(shipId:Int,startTileId:Int, endTileId:Int){
        log("Current Drift: Ship $shipId drifted from tile $startTileId to tile $endTileId.")
    }
    fun  logEventStart(eventId:Int,eventType:EventType){
        log("Event: Event $eventId of type $eventType happened.")
    }
    fun  logTaskAddedToShip(taskId: Int, taskType: TaskType, shipId: Int, tileId: Int) {
        log("Task: Task $taskId of type $taskType with ship $shipId is added with destination $tileId.")
    }
    fun  logRewardReceived(taskId: Int, shipId: Int, rewardType: RewardType){
        log("Reward: Task $taskId: Ship $shipId received reward of type $rewardType.")
    }
    fun  logSimulationStatisticsCalculated() {
        log("Simulation Info: Simulation statistics are calculated.")
    }
    fun logSimulationStatistics(){
        //Yet to be completed as no idea where we can pass arguments here a list ot call this function iteratively in simulator
        TODO()
    }
    private fun statsForCorporation (corporationId:Int,amount:Int){
        log("Simulation Statistics: Corporation $corporationId collected $amount of garbage.")
    }
    private fun totalPlastic (amount:Int){
        log("Simulation Statistics: Total amount of plastic collected: $amount")
    }
    private fun totalOil(amount:Int){
        log("Simulation Statistics: Total amount of oil collected: $amount.")
    }
    private fun totalChemicals(amount:Int){
        log("Simulation Statistics: Total amount of chemicals collected: $amount.")
    }
    private fun totalGarbageInOcean(amount:Int){
        log("Simulation Statistics: Total amount of garbage still in the ocean: $amount.")
    }
}
