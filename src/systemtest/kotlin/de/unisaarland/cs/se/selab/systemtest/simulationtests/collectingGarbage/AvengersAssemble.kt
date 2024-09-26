package de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/** Test Sending Ships to Plastic Garbage */
class AvengersAssemble : ExampleSystemTestExtension() {
    override val corporations = "AvengersAssemble/corporation.json"
    override val description = "Move enough ships for collecting"
    override val map = "AvengersAssemble/map.json"
    override val maxTicks = 5
    override val name = "Avengers Assemble!"
    override val scenario = "AvengersAssemble/scenario.json"
    override suspend fun run() {
        assertNextLine("Initialization Info: map.json successfully parsed and validated.")
    }
}