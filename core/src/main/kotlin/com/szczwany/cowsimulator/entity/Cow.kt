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
import com.szczwany.cowsimulator.enums.StateType
import kotlin.math.abs

infix fun Vector2.distance(other: Vector2) = Math.sqrt(Math.pow((abs(other.x) - abs(this.x)).toDouble(), 2.0) + Math.pow((abs(other.y) - abs(this.y)).toDouble(), 2.0))

class Cow(position: Vector2, width: Float, height: Float) : Entity(position, width, height, EntityType.ALIVE)
{
    // move variables - test
    private var start: Vector2 = Vector2.Zero
    private var end = Vector2.Zero
    private var moving = false
    private val speed = 200F
    // end

    private var currentAnimationFinished= false

    private var time = 0F

    private var direction = Direction.RIGHT
    private var state = StateType.IDLE

    private var shadowIndex = 0
        get() = direction

    private var shadowTextureRegion: TextureRegion? = null
        get() = CowSimulatorGame.assetLibrary.cowShadowRegions[shadowIndex]

    override var entityTextureRegion: TextureRegion? = null

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

    override fun draw(spriteBatch: SpriteBatch)
    {
        if(entityTextureRegion != null)
        {
            spriteBatch.draw(shadowTextureRegion, position.x, position.y, width, height)
            spriteBatch.draw(entityTextureRegion, position.x, position.y, width, height)
        }
    }

    private fun moveToDestination(destination: Vector2)
    {
        start = Vector2(position)
        end = Vector2(destination)

        moving = true
    }

    private fun walk(deltaTime: Float)
    {
        if(moving)
        {
            val distance = start.distance(end)
            val angle = Vector2(end).sub(start).nor()

            position.add(angle.scl(speed * deltaTime))

            if(start.distance(position) >= distance)
            {
                position = Vector2(end)

                moving = false
                start = Vector2.Zero
                end = Vector2.Zero
            }
        }
    }

    override fun update(deltaTime: Float)
    {
        setTextureRegion()

        walk(deltaTime)

        if(state != StateType.IDLE) time += deltaTime else time = 0F

        if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4) && !moving)
        {
            state = StateType.WALK

            if(direction != Direction.LEFT)
            {
                direction = Direction.LEFT
            }

            moveToDestination(Vector2(1F, 1F))
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6))
        {
            state = StateType.WALK

            if(direction != Direction.RIGHT)
            {
                direction = Direction.RIGHT
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8))
        {
            state = StateType.WALK

            if(direction != Direction.UP)
            {
                direction = Direction.UP
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2))
        {
            state = StateType.WALK

            if(direction != Direction.DOWN)
            {
                direction = Direction.DOWN
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5))
        {
            state = StateType.EAT
        }
        else if(currentAnimationFinished)
        {
            state = StateType.IDLE
        }
    }

    private fun setTextureRegion()
    {
        val index = if(state == StateType.EAT) direction + 4 else direction
        val loop = state == StateType.WALK

        currentAnimationFinished = CowSimulatorGame.assetLibrary.cowAnimations[index].isAnimationFinished(time)

        entityTextureRegion = CowSimulatorGame.assetLibrary.cowAnimations[index].getKeyFrame(time, loop)
    }
}