package com.szczwany.cowsimulator.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.szczwany.cowsimulator.Settings.PASTURE_HEIGHT
import com.szczwany.cowsimulator.Settings.PASTURE_WIDTH
import com.szczwany.cowsimulator.astar.AStar
import com.szczwany.cowsimulator.astar.Node
import com.szczwany.cowsimulator.world.Pasture

class GameScreen : BaseScreen()
{
    private lateinit var spriteBatch: SpriteBatch

    // private val pasture = Pasture(PASTURE_WIDTH, PASTURE_HEIGHT)

    private var done = false

    private val width = 10
    private val height = 8

    private val nodes = generateAStar(width, height)

    private val aStar = AStar(width, height, nodes)

    private fun generateAStar(width: Int, height: Int): MutableList<Node>
    {
        val nodes = mutableListOf<Node>()

        for (y in 0 until height)
        {
            for (x in 0 until width)
            {
                val node = Node(x, y, true)

                nodes.add(node)
            }
        }

        return nodes
    }

    override fun show()
    {
        spriteBatch = SpriteBatch()
    }

    private fun draw(deltaTime: Float)
    {
        // pasture.draw(spriteBatch)

        if (!done)
        {
            val startNode = Node(0,0,true)
            val goalNode = Node(1,7,true)

            aStar.setStartNode(startNode)
            aStar.setGoalNode(goalNode)

            for (y in 0 until height)
            {
                for (x in 0 until width)
                {
                    val index = x + width * y
                    val currentNode = nodes[index]

                    print("($currentNode) ")
                }

                println()
            }

            for (path in aStar.findPath())
            {
                println(path.toString())
            }

            done = true
        }
    }

    override fun render(deltaTime: Float)
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // pasture.update(deltaTime)

        spriteBatch.begin()
        draw(deltaTime)
        spriteBatch.end()
    }

    override fun dispose()
    {
        spriteBatch.dispose()
    }
}