package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

class Cow(private val position: Vector2, private val texture: Texture)
{
    fun draw(spriteBatch: SpriteBatch)
    {
        spriteBatch.draw(texture, position.x, position.y, texture.width.toFloat(), texture.height.toFloat())
    }

    fun dispose()
    {
        texture.dispose()
    }
}