package de.unisaarland.cs.se.selab.events

import de.unisaarland.cs.se.selab.corporation.Corporation

/** Abstract class regarding events. */
abstract class Event(open val id: Int, protected open val fireTick: Int) {
    /** Tick an event, if returned true the event no longer needs ticked. */
    abstract fun actUponTick(currentTick: Int, corporations: List<Corporation>): Boolean
}
