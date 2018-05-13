package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.CowSimulatorGame
import com.szczwany.cowsimulator.Settings.GAME_TILE_SIZE
import com.szczwany.cowsimulator.Settings.GRASS_GROW_TIME
import com.szczwany.cowsimulator.enums.TileType

fun getTextureRegion(tileType: TileType) = CowSimulatorGame.assetLibrary.getGrassTerrainTextureRegion(tileType)

class Tile(private val position: Vector2, private val basicTileType: TileType, private var actionTileType: TileType)
{
    var growTime = 0F
        set(value)
        {
            if(actionTileType == TileType.LOWGRASS0)
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

    private var basicTileTextureRegion: TextureRegion? = null
            get() = getTextureRegion(basicTileType)
    private var actionTileTextureRegion: TextureRegion? = null
            get() = getTextureRegion(actionTileType)

    fun draw(spriteBatch: SpriteBatch)
    {
        spriteBatch.draw(basicTileTextureRegion, position.x, position.y, GAME_TILE_SIZE, GAME_TILE_SIZE)

        if(actionTileTextureRegion != null)
        {
            spriteBatch.draw(actionTileTextureRegion, position.x, position.y, GAME_TILE_SIZE, GAME_TILE_SIZE)
        }
    }

    fun eatTallGrass()
    {
        if(actionTileTextureRegion != null && actionTileType == TileType.TALLGRASS0)
        {
            actionTileType = TileType.NONE
        }
    }

    fun plantLowGrass()
    {
        if(actionTileType == TileType.NONE)
        {
            actionTileType = TileType.LOWGRASS0
        }
    }

    private fun growTallGrass()
    {
        if(actionTileType == TileType.LOWGRASS0)
        {
            actionTileType = TileType.TALLGRASS0
        }
    }
}
