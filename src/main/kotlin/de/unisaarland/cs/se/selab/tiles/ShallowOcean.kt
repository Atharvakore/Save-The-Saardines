package de.unisaarland.cs.se.selab.tiles

class ShallowOcean(
    id: Int,
    pos: Vec2D,
    private val adjacentTiles: List<Tile>,
    private var shipTransversable: Boolean,
    private var garbage: List<Garbage>,
    override var amountOfGarbageDriftedThisTick: Int,
    override var amountOfShipsDriftedThisTick: Int,
) : Tile(
        id,
        pos,
        adjacentTiles,
        shipTransversable,
        garbage,
        amountOfGarbageDriftedThisTick,
        amountOfShipsDriftedThisTick,
    )
