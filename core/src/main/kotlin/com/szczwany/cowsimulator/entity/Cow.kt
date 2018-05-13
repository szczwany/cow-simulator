package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.Settings.GAME_COW_SIZE
import com.szczwany.cowsimulator.Settings.WINDOW_HEIGHT
import com.szczwany.cowsimulator.Settings.WINDOW_WIDTH

class Cow(position: Vector2, private val texture: TextureRegion?) : Entity(position)
{
    private val speed = 300F

    override var position = position
        set(value)
        {
            field = value

            if(field.x > WINDOW_WIDTH - GAME_COW_SIZE + 70)
            {
                field.x = WINDOW_WIDTH - GAME_COW_SIZE + 70
            }

            if(field.x < -40) field.x = -40F

            if(field.y > WINDOW_HEIGHT - GAME_COW_SIZE + 80)
            {
                field.y = WINDOW_HEIGHT - GAME_COW_SIZE + 80
            }

            if(field.y < -80) field.y = -80F
        }

    private fun walk(deltaTime: Float, dirX: Int, dirY: Int)
    {
        position = Vector2(position.x + speed * deltaTime * dirX, position.y + speed * deltaTime * dirY)
    }

    override fun draw(spriteBatch: SpriteBatch)
    {
        spriteBatch.draw(texture, position.x, position.y, GAME_COW_SIZE, GAME_COW_SIZE)
    }

    override fun update(deltaTime: Float)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4))
        {
            walk(deltaTime, -1, 0)
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6))
        {
            walk(deltaTime, 1, 0)
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8))
        {
            walk(deltaTime, 0, 1)
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2))
        {
            walk(deltaTime, 0, -1)
        }
    }
}