package de.unisaarland.cs.se.selab.systemtest.simulationtests.drifting

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension
/** */
class MoreMorePlastic : ExampleSystemTestExtension() {
    override val description = "Test drifting of Plastic To Ship Tile"
    override val corporations = "driftAll/corporationDriftToMePlasticEdition.json"
    override val scenario = "driftAll/scenarioDriftToMePlasticEdition.json"
    override val map = "driftAll/map.json"
    override val name = "More More Plastic!!"
    override val maxTicks = 5
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
        assertNextLine(
            "Initialization Info: corporationDriftToMePlasticEdition.json successfully parsed and validated."
        )
        assertNextLine("Initialization Info: scenarioDriftToMePlasticEdition.json successfully parsed and validated.")
        assertNextLine("Simulation Info: Simulation started.")
        skipUntilString("Garbage Collection: Ship 1 collected 150 of garbage PLASTIC with 4.")
        skipLines(7)
        assertNextLine("Garbage Collection: Ship 1 collected 150 of garbage PLASTIC with 5.")
        skipUntilString("Simulation Statistics: Total amount of garbage still in the ocean: 14600.")
        assertEnd()
    }
}
