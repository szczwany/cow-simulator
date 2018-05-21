package com.szczwany.cowsimulator.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.szczwany.cowsimulator.Settings.PASTURE_HEIGHT
import com.szczwany.cowsimulator.Settings.PASTURE_WIDTH
import com.szczwany.cowsimulator.world.Pasture

class GameScreen : BaseScreen()
{
    private lateinit var spriteBatch: SpriteBatch

    private val pasture = Pasture(PASTURE_WIDTH, PASTURE_HEIGHT)

    override fun show()
    {
        spriteBatch = SpriteBatch()
    }

    private fun draw(deltaTime: Float)
    {
        pasture.draw(spriteBatch)
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