package com.szczwany.cowsimulator.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.szczwany.cowsimulator.Settings.TEXTURE_TILE_SIZE
import com.szczwany.cowsimulator.enums.TileType

class AssetLibrary
{

    private val grassTerrain32x32: Texture = Texture(Gdx.files.internal("grass_tileset_32x32.png"))
    private val grassTerrainMap: MutableMap<TileType, TextureRegion> = hashMapOf()

    init
    {
        initGrassTerrain()
    }

    private fun initGrassTerrain()
    {
        initTileRegion(0,0, TileType.GRASS0)
        initTileRegion(1,0, TileType.GRASS1)
        initTileRegion(2,0, TileType.GRASS2)
        initTileRegion(3,0, TileType.GRASS3)
        initTileRegion(5,0, TileType.FLOWER0)
        initTileRegion(7,0, TileType.FLOWER1)
        initTileRegion(0,2, TileType.LOWGRASS0)
        initTileRegion(1,2, TileType.TALLGRASS0)
    }

    private fun initTileRegion(x: Int, y: Int, tileType: TileType)
    {
        grassTerrainMap[tileType] = TextureRegion(grassTerrain32x32, x * TEXTURE_TILE_SIZE, y * TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE)
    }

    fun getGrassTerrainTextureRegion(tileType: TileType) : TextureRegion? = if(tileType != TileType.NONE) grassTerrainMap[tileType] else null

    fun dispose()
    {
        grassTerrain32x32.dispose()
    }
}