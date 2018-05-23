package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.CowSimulatorGame
import com.szczwany.cowsimulator.Settings.WINDOW_HEIGHT
import com.szczwany.cowsimulator.Settings.WINDOW_WIDTH
import com.szczwany.cowsimulator.enums.Direction
import com.szczwany.cowsimulator.enums.EntityType
import com.szczwany.cowsimulator.enums.StateType
import java.util.*
import kotlin.math.abs

infix fun Vector2.distance(other: Vector2) = Math.sqrt(Math.pow((abs(other.x) - abs(this.x)).toDouble(), 2.0) + Math.pow((abs(other.y) - abs(this.y)).toDouble(), 2.0))

class Cow(position: Vector2, width: Float, height: Float) : Entity(position, width, height, EntityType.ALIVE)
{
    private val cowFont = CowSimulatorGame.assetLibrary.cowMessageFont
    private val cowCloud = CowSimulatorGame.assetLibrary.cowMessageCloud

    private var start = Vector2.Zero
    private var end = Vector2.Zero
    private var isMoving = false
    private var onDestination = true
    private val speed = 200F

    private var distanceWalked = 0F

    private var time = 0F

    private var currentPlant: Plant? = null

    val isHungry
        get() = hungerQuantity > 50

    private var hungerQuantity = 100F
        set(value)
        {
            field = if(value > 100) 100F else if(value < 0) 0F else value
        }

    private var directionIndex = Direction.DOWN
    private var state = StateType.IDLE

    private var shadowIndex = 0
        get() = directionIndex

    private var shadowTextureRegion: TextureRegion? = null
        get() = CowSimulatorGame.assetLibrary.cowShadowRegions[shadowIndex]

    private var currentAnimation = CowSimulatorGame.assetLibrary.cowAnimations[directionIndex]

    override fun draw(spriteBatch: SpriteBatch)
    {
        spriteBatch.draw(shadowTextureRegion, position.x - width / 2, position.y - height / 2, width, height)
        spriteBatch.draw(currentAnimation.getKeyFrame(time, state == StateType.WALK), position.x - width / 2, position.y - height / 2, width, height)
    }

    fun setCurrentPlant(plant: Plant)
    {
        if(currentPlant == null)
        {
            currentPlant = plant
        }
    }

    private fun moveToDestination(destination: Vector2)
    {
        start = Vector2(position)
        end = Vector2(destination)

        isMoving = true
        onDestination = false
        state = StateType.WALK
    }

    private fun walk(deltaTime: Float)
    {
        val distance = start.distance(end)
        val direction = Vector2(end).sub(start).nor()

        position.add(direction.scl(speed * deltaTime))

        if(start.distance(position) >= distance)
        {
            position = Vector2(end)

            isMoving = false
            onDestination = true

            distanceWalked = distance.toFloat()
        }
    }

    override fun update(deltaTime: Float)
    {
        if (state != StateType.IDLE) time += deltaTime else time = 0F

        setDirectionIndex()
        setCurrentAnimation()

        if (!onDestination && isMoving)
        {
            walk(deltaTime)
        }
        else
        {
            if (state == StateType.IDLE)
            {
                val random = Random()

                if (random.nextInt(200) < 1 || hungerQuantity > 75) // jesli random albo mocno glodna to pochodz sobie lub jedz else stoj
                {
                    val nextPosition: Vector2

                    if (isHungry) //   eat grass!
                    {
                        nextPosition = Vector2(currentPlant!!.getCenter())
                    }
                    else   // wander
                    {
                        nextPosition = Vector2(random.nextInt((WINDOW_WIDTH - 300) + 200).toFloat(),
                                random.nextInt((WINDOW_HEIGHT - 300) + 200).toFloat())
                    }

                    moveToDestination(nextPosition)
                }

            }
            else if (state == StateType.WALK)
            {
                time = 0F

                state = if (!isHungry) StateType.IDLE else StateType.EAT

                hungerQuantity += distanceWalked / 100F
            }
            else if (state == StateType.EAT && currentAnimation.isAnimationFinished(time))
            {
                currentPlant!!.eatTallGrass()
                hungerQuantity -= currentPlant!!.foodQuantity
                currentPlant = null

                state = StateType.IDLE
            }
        }
    }

    private fun setCurrentAnimation()
    {
        val index = if(state == StateType.EAT) directionIndex + 4 else directionIndex

        currentAnimation = CowSimulatorGame.assetLibrary.cowAnimations[index]

    }

    private fun setDirectionIndex()
    {
        var angle = Math.toDegrees(Math.atan2(end.y - start.y.toDouble(), end.x - start.x.toDouble())).toFloat()

        if (angle < 0)
        {
            angle += 360f
        }

        directionIndex =
                if (angle > 50 && angle < 130) Direction.UP
                else if (angle > 230 && angle < 310) Direction.DOWN
                else if (angle > 130 && angle < 230) Direction.LEFT
                else Direction.RIGHT
    }

    // testowa chmurka z wiadomoscia od krowy
    fun drawCowMessage(spriteBatch: SpriteBatch)
    {
        val message = if (state == StateType.IDLE) "Co by tu..." else if (state == StateType.EAT) "Jedzonkoo" else ""

        if (!message.isEmpty())
        {
            cowFont.color = Color.BLACK

            val x = position.x + 20
            val y = position.y + 30

            spriteBatch.draw(cowCloud, x, y)
            cowFont.draw(spriteBatch, message, x + 15, y + 70)
        }
    }
}