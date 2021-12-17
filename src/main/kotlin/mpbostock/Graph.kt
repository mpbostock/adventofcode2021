package mpbostock

object Graph {
    // Dijkstra's algorithm as extension method on list of nodes
    fun <T> List<T>.findShortestPath(
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

            for (adjacentNode in adjacentNodesProvider(node)) {
                val alt = weight + (nodeWeightProvider(adjacentNode))
                if (alt < weights.getValue(adjacentNode)) weights[adjacentNode] = alt
            }
        }
        return weights.getValue(endNode)
    }

}