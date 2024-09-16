package de.unisaarland.cs.se.selab.tiles

/**
 * Basic Current
 */
data class Current(
    val speed: Int?,
    val direction: Direction?,
    val intensity: Int?,
){
    /**
     * returns Intensity
     */
   public fun getIntensity (): Int? {
        return this.intensity
    }
}
