package com.szczwany.cowsimulator.astar

import java.util.PriorityQueue
import java.util.ArrayList



class AStar(private val width: Int, private val height: Int, private val nodes: List<Node>, private val startNode: Node, private val goalNode: Node)
{
    private val horizontalCost = 10
    private val diagonalCost = 14
    
    private var openList = PriorityQueue<Node>()
    private var closedList = mutableListOf<Node>()

    init
    {
        setNodes()
    }

    private fun setNodes()
    {
        for (node in nodes)
        {
            node.calculateH(goalNode)
        }
    }

    fun findPath(): List<Node>
    {
        openList.add(startNode)

        while (openList.isNotEmpty())
        {
            val currentNode = openList.poll()

            closedList.add(currentNode)

            if (currentNode.x == goalNode.x && currentNode.y == goalNode.y)
            {
                return getPath(currentNode)
            }
            else
            {
                addAdjacentNodes(currentNode)
            }
        }

        return emptyList()
    }

    private fun getPath(currentNode: Node): List<Node>
    {
        var tempNode = currentNode
        val path = ArrayList<Node>()
        path.add(tempNode)

        do
        {
            val parent = tempNode.parent
            if (parent != null)
            {
                path.add(0, parent)
                tempNode = parent
            }
        }
        while (parent != null)

        return path
    }

    private fun addAdjacentNodes(currentNode: Node)
    {
        addAdjacentUpperRow(currentNode)
        addAdjacentMiddleRow(currentNode)
        addAdjacentLowerRow(currentNode)
    }

    private fun addAdjacentLowerRow(currentNode: Node)
    {
        val row = currentNode.y
        val col = currentNode.x
        val lowerRow = row - 1

        if (lowerRow >= 0)
        {
            if (col - 1 >= 0)
            {
                checkNode(currentNode, col - 1, lowerRow, diagonalCost) // Comment this line if diagonal movements are not allowed
            }
            if (col + 1 <= width)
            {
                checkNode(currentNode, col + 1, lowerRow, diagonalCost) // Comment this line if diagonal movements are not allowed
            }
            checkNode(currentNode, col, lowerRow, horizontalCost)
        }
    }

    private fun addAdjacentMiddleRow(currentNode: Node)
    {
        val row = currentNode.y
        val col = currentNode.x
        val middleRow = row

        if (col - 1 >= 0)
        {
            checkNode(currentNode, col - 1, middleRow, horizontalCost)
        }
        if (col + 1 <= width)
        {
            checkNode(currentNode, col + 1, middleRow, horizontalCost)
        }
    }

    private fun addAdjacentUpperRow(currentNode: Node)
    {
        val row = currentNode.y
        val col = currentNode.x
        val upperRow = row + 1

        if (upperRow <= height)
        {
            if (col - 1 >= 0)
            {
                checkNode(currentNode, col - 1, upperRow, diagonalCost)
            }
            if (col + 1 <= width)
            {
                checkNode(currentNode, col + 1, upperRow, diagonalCost)
            }
            checkNode(currentNode, col, upperRow, horizontalCost)
        }
    }

    private fun checkNode(currentNode: Node, col: Int, row: Int, cost: Int)
    {
        val index = col * (width + 1) + row

        val adjacentNode = nodes[index]
        if (adjacentNode.isPassable && !closedList.contains(adjacentNode))
        {
            if (!openList.contains(adjacentNode))
            {
                adjacentNode.setNodeData(currentNode, cost)
                openList.add(adjacentNode)
            }
            else
            {
                val changed = adjacentNode.checkBetterPath(currentNode, cost)

                if (changed)
                {
                    openList.remove(adjacentNode)
                    openList.add(adjacentNode)
                }
            }
        }
    }
}