package de.unisaarland.cs.se.selab.events

abstract class Event (private val id: Int, private val fireInt: Int)  {
    abstract fun actUponTick(currentTick: Int): Boolean
}