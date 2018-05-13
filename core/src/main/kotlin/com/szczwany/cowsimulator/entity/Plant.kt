package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.CowSimulatorGame
import com.szczwany.cowsimulator.Settings.GAME_PLANT_SIZE
import com.szczwany.cowsimulator.Settings.GAME_TILE_SIZE
import com.szczwany.cowsimulator.Settings.GRASS_GROW_TIME
import com.szczwany.cowsimulator.enums.PlantType

fun getTextureRegion(plantType: PlantType) = CowSimulatorGame.assetLibrary.getPlantTextureRegion(plantType)

class Plant(position: Vector2, private var plantTileType: PlantType) : Entity(position)
{
    private var plantTextureRegion: TextureRegion? = null
        get() = getTextureRegion(plantTileType)

    var growTime = 0F
        set(value)
        {
            if(plantTileType == PlantType.LOWGRASS0)
            {
                if(field > GRASS_GROW_TIME)
                {
                    growTallGrass()

                    field = 0F
                }
                else
                {
                    field += value
                }
            }
        }

    override fun draw(spriteBatch: SpriteBatch)
    {
        if(plantTextureRegion != null)
        {
            spriteBatch.draw(plantTextureRegion, position.x, position.y, GAME_PLANT_SIZE, GAME_PLANT_SIZE)
        }
    }

    fun eatTallGrass()
    {
        if(plantTileType == PlantType.TALLGRASS0)
        {
            plantTileType = PlantType.NONE
        }
    }

    fun plantLowGrass()
    {
        if(plantTileType == PlantType.NONE)
        {
            plantTileType = PlantType.LOWGRASS0
        }
    }

    private fun growTallGrass()
    {
        if(plantTileType == PlantType.LOWGRASS0)
        {
            plantTileType = PlantType.TALLGRASS0
        }
    }

    fun bounds() = Rectangle(position.x, position.y, GAME_PLANT_SIZE, GAME_PLANT_SIZE)
}
