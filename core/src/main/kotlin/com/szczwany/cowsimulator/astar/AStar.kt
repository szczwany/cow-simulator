package com.szczwany.cowsimulator.astar

import java.util.PriorityQueue
import java.util.ArrayList



class AStar(private val width: Int, private val height: Int, private val nodes: List<Node>)
{
    private lateinit var startNode: Node
    private lateinit var goalNode: Node

    private val horizontalCost = 10
    private val diagonalCost = 14
    
    private var openList = PriorityQueue<Node>()
    private var closedList = mutableListOf<Node>()

    init
    {
        // setNodes()
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
        setNodes()

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
        val x = currentNode.x
        val y = currentNode.y
        val lowerRow = y - 1

        if (lowerRow >= 0)
        {
            if (x - 1 >= 0)
            {
                checkNode(currentNode, x - 1, lowerRow, diagonalCost) // Comment this line if diagonal movements are not allowed
            }
            if (x + 1 < width)
            {
                checkNode(currentNode, x + 1, lowerRow, diagonalCost) // Comment this line if diagonal movements are not allowed
            }
            checkNode(currentNode, x, lowerRow, horizontalCost)
        }
    }

    private fun addAdjacentMiddleRow(currentNode: Node)
    {
        val x = currentNode.x
        val y = currentNode.y
        val middleRow = y

        if (x - 1 >= 0)
        {
            checkNode(currentNode, x - 1, middleRow, horizontalCost)
        }
        if (x + 1 < width)
        {
            checkNode(currentNode, x + 1, middleRow, horizontalCost)
        }
    }

    private fun addAdjacentUpperRow(currentNode: Node)
    {
        val x = currentNode.x
        val y = currentNode.y
        val upperRow = y + 1

        if (upperRow < height)
        {
            if (x - 1 >= 0)
            {
                checkNode(currentNode, x - 1, upperRow, diagonalCost)
            }
            if (x + 1 < width)
            {
                checkNode(currentNode, x + 1, upperRow, diagonalCost)
            }
            checkNode(currentNode, x, upperRow, horizontalCost)
        }
    }

    private fun checkNode(currentNode: Node, x: Int, y: Int, cost: Int)
    {
        val index = x + width * y

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

    fun setStartNode(startNode: Node)
    {
        this.startNode = startNode
    }

    fun setGoalNode(goalNode: Node)
    {
        this.goalNode = goalNode
    }
}