package de.unisaarland.cs.se.selab


class Simulation (
        private val corporations: List<Corporation>,
        private val allEvents:    List<Event>,
        private val maxTick:      Int,
        private val sea:          Sea,
) {
    private var tick: Int

    public fun getCorporations() : List<Corporations> {
        return this.corporations
    }

    public fun getAllEvents() : List<Event> {
        return this.allEvents
    }

    public fun getSea() : Sea {
        return this.sea
    }

    /**
     * starts the whole simulation and enters a loop that runs tick() until maxTick is reached
     */
    /**
     * TODO: Implement.
     */
    fun start(): Unit {
        TODO("")
    }

    /**
     * each call to tick corresponds to one tick in the simulation
     * it handles all the simulation logic by calling the different methods in order
     */
    /**
     * TODO: Implement.
     */
    fun tick(): Unit {
        TODO("")
    }

    /**
     * iterates over all corporations and calls run() on them
     */
    /**
     * TODO: Implement.
     */
    private fun runCorporations(): Unit {
        TODO("")
    }

    /**
     * handles the drift garbage logic
     */
    /**
     * TODO: Implement.
     */
    private fun driftGarbage(): Unit {
        TODO("")
    }

    /**
     * handles the drift ships logic
     */
    /**
     * TODO: Implement.
     */
    private fun driftShips(): Unit {
        TODO("")
    }

    /**
     * starts new tasks and updates active tasks
     */
    /**
     * TODO: Implement.
     */
    private fun processTasks(): Unit {
        TODO("")
    }
    /**
     * iterates over all events and call actUponTick on them
     */
    /**
     * TODO: Implement.
     */
    private fun processEvents() : Unit {
        TODO("")
    }
}

