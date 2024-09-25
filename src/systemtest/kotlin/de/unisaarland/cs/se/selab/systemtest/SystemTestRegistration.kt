package de.unisaarland.cs.se.selab.systemtest

import de.unisaarland.cs.se.selab.systemtest.basictests.ExampleSystemTest
import de.unisaarland.cs.se.selab.systemtest.basictests.sequencetick25.SequenceTick25CorporationTest
import de.unisaarland.cs.se.selab.systemtest.basictests.sequencetick25.SequenceTick25ParsingTest
import de.unisaarland.cs.se.selab.systemtest.basictests.sequencetick25.SequenceTick25StatisticsTest
import de.unisaarland.cs.se.selab.systemtest.basictests.sequencetick25.SequenceTick25TickTest
import de.unisaarland.cs.se.selab.systemtest.runner.SystemTestManager
import de.unisaarland.cs.se.selab.systemtest.simulationtests.DriftALL
import de.unisaarland.cs.se.selab.systemtest.simulationtests.DriftGarbageMultipleCurrents
import de.unisaarland.cs.se.selab.systemtest.simulationtests.DriftGarbageOnLandTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.DriftShipsTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.SimulatePirateAttack
import de.unisaarland.cs.se.selab.systemtest.simulationtests.SimulateStormTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.StormOverMultipleTiles
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.CollectingChemicalsTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.CollectingGarbageTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.CollectingOilTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.CollectingPlasticTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.MoveNearHome
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.ReturnToHomeWater
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.CorpNoShips
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.CorporationWhereShip
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.EverythingInOne
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.HarborNoCorp
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.IncorrectEventIds
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.IncorrectPropertiesOfPirateAttack
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.IncorrectPropertiesTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.IncorrectShipsGarbages
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.IncorrectShipsIds
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.LandNextDeepOcean
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.OneIDTwoTasks
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.OneTileNoHarbor
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.ShipAmongUs
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.ShoreNoHarbor
import de.unisaarland.cs.se.selab.systemtest.simulationtests.unloading.UnloadingChemicalsTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.unloading.UnloadingPlasticTest

/** The class which will register the tests for Testing on Course Implementation */
object SystemTestRegistration {

    // Seperated tests because of Detekt
    private val testsForReferenceImpl = listOf(
        StormOverMultipleTiles(),
        DriftGarbageMultipleCurrents(),
        ExampleSystemTest(),
        ExampleSystemTest(),
        MoveNearHome(),
        ReturnToHomeWater(),
        SequenceTick25ParsingTest(),
        SequenceTick25CorporationTest(),
        SequenceTick25TickTest(),
        SequenceTick25StatisticsTest(),
        CollectingGarbageTest(),
        CollectingOilTest(),
        CollectingPlasticTest(),
        CollectingChemicalsTest(),
        UnloadingChemicalsTest(),
        UnloadingPlasticTest(),
        IncorrectPropertiesTest(),
        ShipAmongUs(),
        IncorrectPropertiesOfPirateAttack(),
        IncorrectShipsGarbages(),
        IncorrectShipsIds(),
        IncorrectEventIds(),
        EverythingInOne(),
        LandNextDeepOcean(),
        DriftShipsTest(),
        SimulatePirateAttack(),
        SimulateStormTest(),
        DriftGarbageOnLandTest(),
        // Section for tests to check on reference to test later against mutant:
        OneIDTwoTasks(),
        ShoreNoHarbor(),
        CorporationWhereShip(),
        CorpNoShips(),
        OneTileNoHarbor(),
        // DAY 2:
        HarborNoCorp(),
        DriftALL()

    )

    private val testsForMutants = listOf(
        StormOverMultipleTiles(),
        DriftGarbageMultipleCurrents(),
        ExampleSystemTest(),
        ExampleSystemTest(),
        MoveNearHome(),
        ReturnToHomeWater(),
        SequenceTick25ParsingTest(),
        SequenceTick25CorporationTest(),
        SequenceTick25TickTest(),
        SequenceTick25StatisticsTest(),
        CollectingGarbageTest(),
        CollectingOilTest(),
        CollectingPlasticTest(),
        CollectingChemicalsTest(),
        UnloadingChemicalsTest(),
        UnloadingPlasticTest(),
        IncorrectPropertiesTest(),
        ShipAmongUs(),
        IncorrectPropertiesOfPirateAttack(),
        IncorrectShipsGarbages(),
        IncorrectShipsIds(),
        IncorrectEventIds(),
        EverythingInOne(),
        LandNextDeepOcean(),
        DriftShipsTest(),
        SimulatePirateAttack(),
        SimulateStormTest(),
        DriftGarbageOnLandTest(),
        // Section for tests to check on reference to test later against mutant:
        OneIDTwoTasks(),
        ShoreNoHarbor(),
        CorporationWhereShip(),
        CorpNoShips(),
        OneTileNoHarbor(),
        // DAY 2:
        HarborNoCorp()

    )

    /**
     * Register your tests to run against the reference implementation!
     * This can also be used to debug our system test, or to see if we
     * understood something correctly or not (everything should work
     * the same as their reference implementation)
     */
    fun registerSystemTestsReferenceImpl(manager: SystemTestManager) {
        testsForReferenceImpl.forEach {
            manager.registerTest(it)
        }
    }

    /**
     * Register the tests you want to run against the validation mutants here!
     * The test only check validation, so they log messages will only possibly
     * be incorrect during the parsing/validation.
     * Everything after 'Simulation start' works correctly
     */
    fun registerSystemTestsMutantValidation(manager: SystemTestManager) {
        manager.registerTest(ExampleSystemTest())
        manager.registerTest(IncorrectPropertiesTest())
        manager.registerTest(ShipAmongUs())
    }

    /**
     * The same as above, but the log message only (possibly) become incorrect
     * from the 'Simulation start' log onwards
     */
    fun registerSystemTestsMutantSimulation(manager: SystemTestManager) {
        testsForMutants.forEach {
            manager.registerTest(it)
        }
    }
}
