package com.szczwany.cowsimulator.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.CowSimulatorGame
import com.szczwany.cowsimulator.Settings.GAME_COW_SIZE
import com.szczwany.cowsimulator.Settings.GAME_PLANT_SIZE
import com.szczwany.cowsimulator.Settings.GAME_TILE_SIZE
import com.szczwany.cowsimulator.Settings.WINDOW_HEIGHT
import com.szczwany.cowsimulator.Settings.WINDOW_WIDTH
import com.szczwany.cowsimulator.entity.Cow
import com.szczwany.cowsimulator.entity.Entity
import com.szczwany.cowsimulator.entity.Plant
import com.szczwany.cowsimulator.entity.Tile
import com.szczwany.cowsimulator.enums.ActionType
import com.szczwany.cowsimulator.enums.EntityType
import java.util.*

fun pixelToEntityPosition(x: Float, y: Float): Vector2
{
    val posX = (x / GAME_PLANT_SIZE).toInt() * GAME_PLANT_SIZE
    val posY = (y / GAME_PLANT_SIZE).toInt() * GAME_PLANT_SIZE

    return Vector2(posX, posY)
}

class Pasture(private val width: Int, private val height: Int)
{
    private val entityList = mutableListOf<Entity>()

    private val cow = Cow(Vector2(WINDOW_WIDTH / 2F - GAME_COW_SIZE / 2, WINDOW_HEIGHT / 2F - GAME_COW_SIZE / 2),
            GAME_COW_SIZE, GAME_COW_SIZE,
            CowSimulatorGame.assetLibrary.getCowTexture(0))

    init
    {
        generate()
    }

    private fun generate()
    {
        entityList.clear()

        // init grass
        for(y in 0 until height)
        {
            for(x in 0 until width)
            {
                val entityPosition = Vector2(x * GAME_TILE_SIZE, y * GAME_TILE_SIZE)
                val tile = Tile(entityPosition, GAME_TILE_SIZE, GAME_TILE_SIZE, EntityType.GRASS0)

                entityList.add(tile)
            }
        }

        // init entities
        val random = Random()

        for(y in 0 until height)
        {
            for(x in 0 until width)
            {
                if(random.nextBoolean())
                {
                    continue
                }

                val entityPosition = Vector2(x * GAME_PLANT_SIZE, y * GAME_PLANT_SIZE)
                val entityType = EntityType.valueOf(random.nextInt(2) + 8)

                val plant = Plant(entityPosition, GAME_PLANT_SIZE, GAME_PLANT_SIZE, entityType)

                entityList.add(plant)
            }
        }

        entityList.add(cow)
    }

    fun update(deltaTime: Float)
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.G))
        {
            generate()
        }

        for (entity in entityList)
        {
            if(entity is Plant)
            {
                entity.growTime = deltaTime
            }
            else if(entity is Cow)
            {
                entity.update(deltaTime)
            }
        }

        val mouseX = Gdx.input.x.toFloat()
        val mouseY = WINDOW_HEIGHT - Gdx.input.y.toFloat()

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            performPlantAction(mouseX, mouseY, ActionType.EAT)
        }
        else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
        {
            performPlantAction(mouseX, mouseY, ActionType.PLANT)
        }
    }

    fun draw(spriteBatch: SpriteBatch)
    {
        entityList.sort()

        for(entity in entityList)
        {
            entity.draw(spriteBatch)
        }
    }

    private fun performPlantAction(mouseX: Float, mouseY: Float, actionType: ActionType)
    {
        val plant = getCurrentPlant(mouseX, mouseY)

        if(plant != null)
        {
            if (actionType == ActionType.EAT && plant.harvestable)
            {
                entityList.remove(plant)
            }
        }
        else
        {
            if(actionType == ActionType.PLANT)
            {
                val entityPosition = pixelToEntityPosition(mouseX, mouseY)

                entityList.add(Plant(entityPosition, GAME_PLANT_SIZE, GAME_PLANT_SIZE, EntityType.LOWGRASS0))
            }
        }
    }

    private fun getCurrentPlant(x: Float, y: Float) : Plant?
    {
        for (entity in entityList)
        {
            if (entity is Plant && entity.getBounds().contains(x, y))
            {
                return entity
            }
        }

        return null
    }
}
