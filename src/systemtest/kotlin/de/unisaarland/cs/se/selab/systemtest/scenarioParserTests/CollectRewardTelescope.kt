package de.unisaarland.cs.se.selab.systemtest.scenarioParserTests

import de.unisaarland.cs.se.selab.systemtest.utils.ExampleSystemTestExtension

/**
 * Basic 6 Tile Test. deep ocean on right with a current pushing left
 */
class CollectRewardTelescope : ExampleSystemTestExtension() {
    override val description = "A collect task shouldn't give out a telescope"
    override val corporations = "corporationJsons/basicSingleShip3.json"
    override val scenario = "scenarioJsons/invalidScenario/bigPileOneRewardScenarioFalse.json"
    override val map = "mapFiles/mediumMapNoCurrents.json"
    override val name = "CollectRewardTelescope"
    override val maxTicks = 10

    override suspend fun run() {
        assertNextLine(
            "Initialization Info: mediumMapNoCurrents.json success" +
                "fully parsed and validated."
        )
        assertNextLine(
            "Initialization Info: basicSingleShip3.json " +
                "successfully parsed and validated."
        )
        assertNextLine(
            "Initialization Info: bigPileOneRewardScenarioFalse.json " +
                "is invalid."
        )
    }
}
