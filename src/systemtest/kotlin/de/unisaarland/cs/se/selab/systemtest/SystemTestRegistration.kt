package de.unisaarland.cs.se.selab.systemtest

import de.unisaarland.cs.se.selab.systemtest.basictests.ExampleSystemTest
import de.unisaarland.cs.se.selab.systemtest.basictests.SequenceTick25Test
import de.unisaarland.cs.se.selab.systemtest.runner.SystemTestManager
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.IncorrectPropertiesTest
import de.unisaarland.cs.se.selab.systemtest.simulationtests.parserTests.ShipAmongUs

/** The class which will register the tests for Testing on Course Implementation */
object SystemTestRegistration {
    /**
     * Register your tests to run against the reference implementation!
     * This can also be used to debug our system test, or to see if we
     * understood something correctly or not (everything should work
     * the same as their reference implementation)
     */
    fun registerSystemTestsReferenceImpl(manager: SystemTestManager) {
        manager.registerTest(ExampleSystemTest())
        manager.registerTest(SequenceTick25Test())
        manager.registerTest(IncorrectPropertiesTest())
        manager.registerTest(ShipAmongUs())
    }

    /**
     * Register the tests you want to run against the validation mutants here!
     * The test only check validation, so they log messages will only possibly
     * be incorrect during the parsing/validation.
     * Everything after 'Simulation start' works correctly
     */
    fun registerSystemTestsMutantValidation(manager: SystemTestManager) {
        manager.registerTest(ExampleSystemTest())
        manager.registerTest(SequenceTick25Test())
    }

    /**
     * The same as above, but the log message only (possibly) become incorrect
     * from the 'Simulation start' log onwards
     */
    fun registerSystemTestsMutantSimulation(manager: SystemTestManager) {
        manager.registerTest(ExampleSystemTest())
        manager.registerTest(SequenceTick25Test())
    }
}
