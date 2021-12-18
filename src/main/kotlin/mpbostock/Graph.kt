package mpbostock

import mpbostock.Day05.Vector2d

object Graph {
    fun Array<IntArray>.findShortestPath (): Int {
        val height = this.size
        val width = this[0].size
        val weights = Array(height) { IntArray(width) {Int.MAX_VALUE} }
        weights[0][0] = 0
        val coords = allCoords(width, height)
        val queue = ArrayDeque(coords)
        val visited = Array(height) { BooleanArray(width) { false } }

        while (queue.isNotEmpty()) {
            val minWeightCoord = coords.filter { !visited[it.y][it.x] }.minByOrNull { weights[it.y][it.x] }
            val minWeight = weights[minWeightCoord!!.y][minWeightCoord.x]
            queue.remove(minWeightCoord)
            visited[minWeightCoord.y][minWeightCoord.x] = true
            val percent = 1.0 * (coords.size - queue.size) / coords.size * 100
            println(String.format("%.2f", percent))

            for (adjacentCoord in adjacentCoords(minWeightCoord, width, height)) {
                val alt = minWeight + this[adjacentCoord.y][adjacentCoord.x]
                if (alt < weights[adjacentCoord.y][adjacentCoord.x]) weights[adjacentCoord.y][adjacentCoord.x] = alt
            }
        }
        return weights[height - 1][width - 1]
    }

    private fun adjacentCoords(coord: Vector2d, width: Int, height: Int): List<Vector2d> {
        val (x, y) = coord
        val neighbours = emptyList<Vector2d>().toMutableList()
        if (x > 0) neighbours.add(Vector2d(x - 1, y))
        if (x < width - 1) neighbours.add(Vector2d(x + 1, y))
        if (y > 0) neighbours.add(Vector2d(x, y - 1))
        if (y < height - 1) neighbours.add(Vector2d(x, y + 1))
        return neighbours
    }

    private fun allCoords(width: Int, height: Int): List<Vector2d> {
        val coords = emptyList<Vector2d>().toMutableList()
        for (x in 0 until width) {
            for (y in 0 until height) {
                coords.add(Vector2d(x, y))
            }
        }
        return coords
    }
    // Dijkstra's algorithm as extension method on list of nodes
    fun <T> Collection<T>.findShortestPath(
        adjacentNodesProvider: (T) -> List<T>,
        nodeWeightProvider: (T) -> Int,
        startNode: T,
        endNode: T
    ): Int {
        val weights = emptyMap<T, Int>().toMutableMap()
        val queue = ArrayDeque<T>()
        val visitedNodes = emptySet<T>().toMutableSet()
        this.forEach { node ->
            if (node == startNode) weights[node] = 0 else weights[node] = Int.MAX_VALUE
            queue.add(node)
        }

        while (queue.isNotEmpty()) {
            val minWeight = weights.filter { !visitedNodes.contains(it.key) }.minByOrNull { it.value }
            val node = minWeight!!.key
            val weight = minWeight.value
            queue.remove(node)
            visitedNodes.add(node)
//            val percent = 1.0 * (this.size - queue.size) / this.size * 100
//            println(String.format("%.2f", percent))

            for (adjacentNode in adjacentNodesProvider(node)) {
                val alt = weight + (nodeWeightProvider(adjacentNode))
                if (alt < weights.getValue(adjacentNode)) weights[adjacentNode] = alt
            }
        }
        return weights.getValue(endNode)
    }

}