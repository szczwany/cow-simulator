package com.szczwany.cowsimulator.astar

class Node(var x: Int, var y: Int, var isPassable: Boolean) : Comparable<Node>
{
    var h = 0
    var g = 0
    var f = 0

    var parent: Node? = null

    fun calculateH(goalNode: Node)
    {
        h = Math.abs(goalNode.x - x) + Math.abs(goalNode.y - y)
    }

    fun setNodeData(currentNode: Node, cost: Int)
    {
        g = currentNode.g + cost
        parent = currentNode

        calculateFinalCost()
    }

    fun checkBetterPath(currentNode: Node, cost: Int): Boolean
    {
        val currentCost = currentNode.g + cost

        if (currentCost < g)
        {
            setNodeData(currentNode, cost)

            return true
        }

        return false
    }

    private fun calculateFinalCost()
    {
        f = g + h
    }

    override fun compareTo(other: Node): Int
    {
        return if (this.f < other.f) -1 else if (this.f > other.f) 1 else 0
    }

    override fun toString(): String
    {
        return "$x $y"
    }
}