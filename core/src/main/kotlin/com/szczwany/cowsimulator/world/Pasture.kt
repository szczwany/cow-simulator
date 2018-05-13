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
import com.szczwany.cowsimulator.enums.PlantType
import com.szczwany.cowsimulator.enums.TileType
import java.util.*

class Pasture(private val width: Int, private val height: Int)
{
    private val entityList = mutableListOf<Entity>()

    private val cow = Cow(Vector2(WINDOW_WIDTH / 2F - GAME_COW_SIZE / 2, WINDOW_HEIGHT / 2F - GAME_COW_SIZE / 2), CowSimulatorGame.assetLibrary.getCowTexture(0))

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
                val tilePosition = Vector2(x * GAME_TILE_SIZE, y * GAME_TILE_SIZE)

                val tile = Tile(tilePosition, TileType.GRASS0)

                entityList.add(tile)
            }
        }

        // init entities
        val random = Random()

        for(y in 0 until height)
        {
            for(x in 0 until width)
            {
                val tilePosition = Vector2(x * GAME_PLANT_SIZE, y * GAME_PLANT_SIZE)
                val plantTileType = if(random.nextBoolean()) PlantType.valueOf(random.nextInt(2) + 6) else PlantType.NONE

                val plant = Plant(tilePosition, plantTileType)

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
            tileAction(mouseX, mouseY, ActionType.EAT)
        }
        else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
        {
            tileAction(mouseX, mouseY, ActionType.PLANT)
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

    private fun tileAction(mouseX: Float, mouseY: Float, actionType: ActionType)
    {
        val plant = getPlantAroundCords(mouseX, mouseY)

        if(plant != null)
        {
            if (actionType == ActionType.EAT)
            {
                plant.eatTallGrass()
            }
            else if(actionType == ActionType.PLANT)
            {
                plant.plantLowGrass()
            }
        }
    }

    private fun getPlantAroundCords(x: Float, y: Float) : Plant?
    {
        for (entity in entityList)
        {
            if (entity is Plant && entity.bounds().contains(x, y))
            {
                return entity
            }
        }

        return null
    }
}
