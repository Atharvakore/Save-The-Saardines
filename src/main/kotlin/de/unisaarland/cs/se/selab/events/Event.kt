package de.unisaarland.cs.se.selab.events

/** Abstract class for Event**/
abstract class Event(private val id: Int, private val fireTick: Int) {
    abstract fun actUponTick(currentTick: Int): Boolean
}
