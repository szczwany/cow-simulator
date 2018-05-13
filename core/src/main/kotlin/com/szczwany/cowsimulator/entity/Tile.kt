package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.CowSimulatorGame
import com.szczwany.cowsimulator.Settings.GAME_TILE_SIZE
import com.szczwany.cowsimulator.enums.TileType

fun getTextureRegion(tileType: TileType) = CowSimulatorGame.assetLibrary.getTileTextureRegion(tileType)

class Tile(position: Vector2, private val basicTileType: TileType) : Entity(position)
{
    private var basicTileTextureRegion: TextureRegion? = null
            get() = getTextureRegion(basicTileType)

    override fun draw(spriteBatch: SpriteBatch)
    {
        spriteBatch.draw(basicTileTextureRegion, position.x, position.y, GAME_TILE_SIZE, GAME_TILE_SIZE)
    }
}
