package de.unisaarland.cs.se.selab.events

/** Abstract class regarding events. */
abstract class Event(private val id: Int, private val fireTick: Int) {
    /** Tick an event, if returned true the event no longer needs ticked. */
    abstract fun actUponTick(currentTick: Int): Boolean
}
