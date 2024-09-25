package de.unisaarland.cs.se.selab.ships

/**
 * Data class for ship movement logs
 */
data class MovementTuple(
    val moved: Boolean,
    val shipId: Int,
    val velocity: Int,
    val destinationId: Int
)
