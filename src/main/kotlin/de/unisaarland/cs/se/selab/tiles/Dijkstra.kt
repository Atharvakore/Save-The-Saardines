package de.unisaarland.cs.se.selab.tiles

import java.util.PriorityQueue

class Dijkstra(start: Tile) {
    private var predecessor: MutableMap<Tile, Tile> = mutableMapOf()
    private var distances: MutableMap<Tile, Int> = mutableMapOf()

    init {
        val queue = PriorityQueue<Pair<Tile, Int>>(compareBy { it.second })
        queue.add(Pair(start, 0))
        distances[start] = 0
        while (queue.isNotEmpty()) {
            val (current, currentDistance) = queue.poll()
            for (neighbour in current.neighbours) {
                if (neighbour != null) {
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

    fun shortestPathTo(target: Tile): List<Tile> {
        val path = mutableListOf<Tile>()
        var current: Tile? = target
        while (current != null) {
            path.add(current)
            current = predecessor.getOrDefault(current, null)
        }
        return path.reversed()
    }

    fun distanceTo(target: Tile): Int {
        return distances.getOrDefault(target, Int.MAX_VALUE)
    }

    fun hasPathTo(target: Tile): Boolean {
        return distances.containsKey(target)
    }

    fun allDistances(): Map<Tile, Int> {
        return distances
    }

    fun allPaths(): Map<Tile, List<Tile>> {
        return distances.mapValues { shortestPathTo(it.key) }
    }
}