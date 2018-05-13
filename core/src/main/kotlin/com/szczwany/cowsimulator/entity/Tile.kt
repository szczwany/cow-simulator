package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.CowSimulatorGame
import com.szczwany.cowsimulator.Settings.GAME_TILE_SIZE
import com.szczwany.cowsimulator.enums.TileType

class Tile(private val position: Vector2, basicTileType: TileType, actionTileType: TileType)
{
    private var basicTileTextureRegion: TextureRegion? = CowSimulatorGame.assetLibrary.getGrassTerrainTextureRegion(basicTileType)
    private var actionTileTextureRegion: TextureRegion? = CowSimulatorGame.assetLibrary.getGrassTerrainTextureRegion(actionTileType)

    fun draw(spriteBatch: SpriteBatch)
    {
        spriteBatch.draw(basicTileTextureRegion, position.x, position.y, GAME_TILE_SIZE, GAME_TILE_SIZE)

        if(actionTileTextureRegion != null)
        {
            spriteBatch.draw(actionTileTextureRegion, position.x, position.y, GAME_TILE_SIZE, GAME_TILE_SIZE)
        }
    }
}
