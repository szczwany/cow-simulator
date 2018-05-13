package com.szczwany.cowsimulator.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.szczwany.cowsimulator.Settings.TEXTURE_TILE_SIZE
import com.szczwany.cowsimulator.enums.TileType

class AssetsManager
{
    private val terrainSpriteSheet: Texture = Texture(Gdx.files.internal("grass_tileset_16x16.png"))
    private val terrainTexturesMap: MutableMap<TileType, TextureRegion> = hashMapOf()

    init
    {
        initGrassTerrain()
    }

    fun getTerrainTextureRegion(tileType: TileType) = terrainTexturesMap[tileType]

    private fun initGrassTerrain()
    {
        for(index in 0..8)
        {
            terrainTexturesMap[TileType.valueOf("GRASS$index")] = TextureRegion(terrainSpriteSheet, 0, index * TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE)
        }
    }

    fun dispose()
    {
        terrainSpriteSheet.dispose()
        terrainTexturesMap.clear()
    }
}