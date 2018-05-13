package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.CowSimulatorGame
import com.szczwany.cowsimulator.Settings.GAME_COW_SIZE
import com.szczwany.cowsimulator.Settings.WINDOW_HEIGHT
import com.szczwany.cowsimulator.Settings.WINDOW_WIDTH
import com.szczwany.cowsimulator.enums.Direction
import com.szczwany.cowsimulator.enums.EntityType

fun getCowTextureRegion(index: Int) = CowSimulatorGame.assetLibrary.getCowTexture(index)

class Cow(position: Vector2, width: Float, height: Float) : Entity(position, width, height, EntityType.ALIVE)
{
    private var time = 0F

    private var animate = false

    private var direction = Direction.DOWN

    private var shadowIndex = 34

    private var shadowTextureRegion: TextureRegion? = null
        get() = getCowTextureRegion(shadowIndex)

    private var index = direction
        get()
        {
            if(field >= direction + 3)
            {
                animate = false

                return direction
            }

            return field
        }

        set(value)
        {
            animate = true

            field = value
        }

    override var entityTextureRegion: TextureRegion? = null
        get() = getCowTextureRegion(index)

    private val speed = 200F

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
        spriteBatch.draw(shadowTextureRegion, position.x, position.y, width, height)
        spriteBatch.draw(entityTextureRegion, position.x, position.y, width, height)
    }

    override fun update(deltaTime: Float)
    {
        if(animate)
        {
            time += deltaTime

            if (time > 0.2F)
            {
                index++
                time = 0F
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4))
        {
            if(direction != Direction.LEFT)
            {
                direction = Direction.LEFT
                index = direction
                shadowIndex = 33
            }

            walk(deltaTime, -1, 0)
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6))
        {
            if(direction != Direction.RIGHT)
            {
                direction = Direction.RIGHT
                index = direction
                shadowIndex = 35
            }

            walk(deltaTime, 1, 0)
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8))
        {
            if(direction != Direction.UP)
            {
                direction = Direction.UP
                index = direction
                shadowIndex = 32
            }

            walk(deltaTime, 0, 1)
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2))
        {
            if(direction != Direction.DOWN)
            {
                direction = Direction.DOWN
                index = direction
                shadowIndex = 34
            }

            walk(deltaTime, 0, -1)
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_5))
        {
            if(direction == Direction.LEFT)
            {
                direction = Direction.EAT_LEFT
                index = direction
                shadowIndex = 33
            }
            else if(direction == Direction.RIGHT)
            {
                direction = Direction.EAT_RIGHT
                index = direction
                shadowIndex = 35
            }
            else if(direction == Direction.UP)
            {
                direction = Direction.EAT_UP
                index = direction
                shadowIndex = 32
            }
            else if(direction == Direction.DOWN)
            {
                direction = Direction.EAT_DOWN
                index = direction
                shadowIndex = 34
            }
        }
    }
}