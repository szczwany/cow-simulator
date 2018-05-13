package com.szczwany.cowsimulator.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.szczwany.cowsimulator.Settings

class MenuScreen : BaseScreen()
{
    private lateinit var shapeRenderer: ShapeRenderer

    override fun show()
    {
        shapeRenderer = ShapeRenderer()
    }

    override fun render(p0: Float)
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        drawGrid()
    }

    private fun drawGrid()
    {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.RED

        val worldWidth = Settings.WINDOW_WIDTH
        val worldHeight = Settings.WINDOW_HEIGHT

        // horizontal lines
        for(y in 0 until worldHeight step 64)
        {
            shapeRenderer.line(0f, y.toFloat(), worldWidth.toFloat(), y.toFloat())
        }

        // vertical lines
        for(x in 0 until worldWidth step  64)
        {
            shapeRenderer.line(x.toFloat(), 0f, x.toFloat(), worldHeight.toFloat())
        }

        shapeRenderer.end()
    }

    override fun dispose()
    {
        shapeRenderer.dispose()
    }
}