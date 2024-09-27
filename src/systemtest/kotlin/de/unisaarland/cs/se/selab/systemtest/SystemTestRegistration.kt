package de.unisaarland.cs.se.selab.systemtest
import de.unisaarland.cs.se.selab.systemtest.basictests.ExampleSystemTest
import de.unisaarland.cs.se.selab.systemtest.basictests.sequencetick25.SequenceTick25CorporationTest
import de.unisaarland.cs.se.selab.systemtest.basictests.sequencetick25.SequenceTick25ParsingTest
import de.unisaarland.cs.se.selab.systemtest.basictests.sequencetick25.SequenceTick25StatisticsTest
import de.unisaarland.cs.se.selab.systemtest.basictests.sequencetick25.SequenceTick25TickTest
import de.unisaarland.cs.se.selab.systemtest.runner.SystemTestManager
import de.unisaarland.cs.se.selab.systemtest.simulationtests.CollectAndRefuelTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.DriftGarbageMultipleCurrents
import de.unisaarland.cs.se.selab.systemtest.simulationtests.DriftGarbageOnLandTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.DriftShipsTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.SimulatePirateAttack
import de.unisaarland.cs.se.selab.systemtest.simulationtests.SimulateStormTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.StormOverMultipleTiles
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.AvengersAssemble
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.CollectingChemicalsTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.CollectingGarbageTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.CollectingMutliplePlasticTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.CollectingOilTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.CollectingPlasticTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.MoveNearHome
import de.unisaarland.cs.se.selab.systemtest.simulationtests.collectingGarbage.ReturnToHomeWater
import de.unisaarland.cs.se.selab.systemtest.simulationtests.drifting.DriftToMe
import de.unisaarland.cs.se.selab.systemtest.simulationtests.drifting.OilBillionaire
import de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants.CooperateOnceTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants.CoordinatingShipDefaultTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants.CoordinatingTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants.CorpNoMove1Test
import de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants.CorpNoMove2Test
import de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants.CorpVisibilityRangeTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.mutants.CorporationProfessionalObserverTest
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
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.MultiTasking
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.OneIDTwoTasks
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.OneTileNoHarbor
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.ShipAmongUs
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.ShoreNoHarbor
import de.unisaarland.cs.se.selab.systemtest.simulationtests.rewards.ContainerRewardTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.rewards.RadioRewardTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.rewards.TelescopeRewardTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.rewards.TrackerRewardTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.scouting.AdventureTime
import de.unisaarland.cs.se.selab.systemtest.simulationtests.scouting.ExploringPacific
import de.unisaarland.cs.se.selab.systemtest.simulationtests.scouting.GodVision
import de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks.CollectGarbageTaskTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks.CooperateTaskTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks.EventSchedulingTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks.ExploreMapTaskTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks.FindGarbageTaskTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.tasks.HardChoice
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
        // DriftMeBaby(),
        // DriftToMe(),
        // MoreMorePlastic(),
        CollectingMutliplePlasticTest(),
        ContainerRewardTest(),
        RadioRewardTest(),
        TelescopeRewardTest(),
        TrackerRewardTest(),
        CollectGarbageTaskTest(),
        CooperateTaskTest(),
        ExploreMapTaskTest(),
        FindGarbageTaskTest(),
        HardChoice(),
        EventSchedulingTest(),
        CorpNoMove1Test(),
        CorpNoMove2Test(),
        CorpVisibilityRangeTest(),
        CorporationProfessionalObserverTest(),
        CooperateOnceTest(),
        CoordinatingShipDefaultTest(),
        CoordinatingTest(),
        EventSchedulingTest(),
        CollectAndRefuelTest(),
        GodVision(),
        AvengersAssemble(),
        OilBillionaire(),
        DriftToMe(),
        MultiTasking()
    )

    private val testsForMutants = listOf(
        StormOverMultipleTiles(),
        DriftGarbageMultipleCurrents(),
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
        CollectingMutliplePlasticTest(),
        CorpNoMove1Test(),
        CorpNoMove2Test(),
        CorpVisibilityRangeTest(),
        CorporationProfessionalObserverTest(),
        CooperateOnceTest(),
        CoordinatingShipDefaultTest(),
        CollectAndRefuelTest(),
        GodVision(),
        ExploringPacific(),
        AdventureTime(),
        AvengersAssemble(),
        OilBillionaire(),

        )

    /**
     * Register your tests to run against the reference implementation!
     * This can also be used to debug our system test, or to see if we
     * understood something correctly or not (everything should work
     * the same as their reference implementation)
     */
    fun registerSystemTestsReferenceImpl(manager: SystemTestManager) {
       // testsForReferenceImpl.forEach {
         //   manager.registerTest(it)
        //}

        manager.registerTest(CollectAndRefuelTest())
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
