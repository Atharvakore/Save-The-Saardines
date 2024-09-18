package de.unisaarland.cs.se.selab.tiles

import java.util.PriorityQueue

/**
 * An implementation of the Dijkstra algorithm which operates on the graph implicitly defined by the Tiles class.
 */
class Dijkstra(start: Tile) {
    private val predecessor: MutableMap<Tile, Tile> = mutableMapOf()
    private val distances: MutableMap<Tile, Int> = mutableMapOf()
    private val accountForRestrictions = start.restrictions == 0

    init {
        val queue = PriorityQueue<Pair<Tile, Int>>(compareBy { it.second })
        queue.add(Pair(start, 0))
        distances[start] = 0
        while (queue.isNotEmpty()) {
            val (current, currentDistance) = queue.poll()
            for (neighbour in current.adjacentTiles) {
                if (neighbour != null) {
                    if (accountForRestrictions && neighbour.restrictions > 0) {
                        continue
                    }
                    val newDistance = currentDistance + 1 // all edges have weight 1
                    if (newDistance < distances.getOrDefault(neighbour, Int.MAX_VALUE)) {
                        distances[neighbour] = newDistance
                        predecessor[neighbour] = current
                        queue.add(Pair(neighbour, newDistance))
                    }
                }
            }
        }
    }

    /**
     * Obtain the shortest path from the source vertex to the target vertex in the graph.
     * @param target the target vertex
     * @return the shortest path from the source vertex to the target vertex
     * @throws IllegalArgumentException if there is no path to the target vertex
     */
    fun shortestPathTo(target: Tile): List<Tile> {
        require(hasPathTo(target)) { "No path to target" }
        val path = mutableListOf<Tile>()
        var current: Tile? = target
        while (current != null) {
            path.add(current)
            current = predecessor.getOrDefault(current, null)
        }
        return path.reversed()
    }

    /**
     * Obtain the distance from the source vertex to the target vertex in the graph.
     * @param target the target vertex
     * @return the distance from the source vertex to the target vertex, or [Int.MAX_VALUE] if there is no path
     */
    fun distanceTo(target: Tile): Int {
        return distances.getOrDefault(target, Int.MAX_VALUE)
    }

    /**
     * Check if there is a path from the source vertex to the target vertex in the graph.
     * @param target the target vertex
     * @return true if there is a path from the source vertex to the target vertex, false otherwise
     */
    fun hasPathTo(target: Tile): Boolean {
        return distances.containsKey(target)
    }

    /**
     * Compute the distances from the source vertex to all other vertices in the graph.
     * @return a map from each vertex to the distance from the source vertex to that vertex
     */
    fun allDistances(): Map<Tile, Int> {
        return distances
    }

    /**
     * Obtain the shortest path from the source vertex to all other vertices in the graph.
     * @return a map from each vertex to the shortest path from the source vertex to that vertex
     */
    fun allPaths(): Map<Tile, List<Tile>> {
        return distances.mapValues { shortestPathTo(it.key) }
    }
}
