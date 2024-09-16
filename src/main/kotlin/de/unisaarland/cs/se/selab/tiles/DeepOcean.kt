package de.unisaarland.cs.se.selab.tiles
const val FIFTY = 50
/**
 * Implements deep ocean Tile
 */
class DeepOcean(
    id: Int,
    pos: Vec2D,
    adjacentTiles: List<Tile>,
    garbage: List<Garbage>,
    private var current: Current?
) : Tile(
    id,
    pos,
    adjacentTiles,
    garbage,0
) {
    /**
     * gives current
     */
    public fun getCurrent(): Current? = current
    /**
     * Calculates amount which can be drifted  in a single drift in one tick
     */

    public fun amountTOBeDrifted() : Int {
        var intensity = getCurrent()?.getIntensity()
        if (intensity != null) {
            return intensity * FIFTY
        }

        return 0
    }
}
