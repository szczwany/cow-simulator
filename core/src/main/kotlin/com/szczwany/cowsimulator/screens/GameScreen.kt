package com.szczwany.cowsimulator.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.szczwany.cowsimulator.Settings.PASTURE_HEIGHT
import com.szczwany.cowsimulator.Settings.PASTURE_WIDTH
import com.szczwany.cowsimulator.astar.AStar
import com.szczwany.cowsimulator.astar.Node
import com.szczwany.cowsimulator.world.Pasture

class GameScreen : BaseScreen()
{
    private lateinit var spriteBatch: SpriteBatch

    private val pasture = Pasture(PASTURE_WIDTH, PASTURE_HEIGHT)

    private val aStar = AStar(3, 3, arrayListOf(Node(0,0, true),
                                                                Node(1,0, true),
                                                                Node(2,0, true),
                                                                Node(3,0, true),
                                                                Node(0,1, true),
                                                                Node(1,1, true),
                                                                Node(2,1, true),
                                                                Node(3,1, true),
                                                                Node(0,2, true),
                                                                Node(1,2, true),
                                                                Node(2,2, true),
                                                                Node(3,2, true),
                                                                Node(0,3, true),
                                                                Node(1,3, true),
                                                                Node(2,3, true),
                                                                Node(3,3, true)
                                                                ), Node(0, 0, true), Node(3,3, true))

    override fun show()
    {
        spriteBatch = SpriteBatch()
    }

    private fun draw(deltaTime: Float)
    {
        pasture.draw(spriteBatch)

        for (path in aStar.findPath())
        {
            println(path.toString())
        }
    }

    override fun render(deltaTime: Float)
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        pasture.update(deltaTime)

        spriteBatch.begin()
        draw(deltaTime)

        spriteBatch.end()
    }

    override fun dispose()
    {
        spriteBatch.dispose()
    }
}