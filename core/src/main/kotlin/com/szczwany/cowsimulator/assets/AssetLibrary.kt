package com.szczwany.cowsimulator.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.szczwany.cowsimulator.Settings.TEXTURE_COW_SIZE
import com.szczwany.cowsimulator.Settings.TEXTURE_TILE_SIZE
import com.szczwany.cowsimulator.enums.TileType

class AssetLibrary
{

    private val grassTerrain32x32: Texture = Texture(Gdx.files.internal("grass_tileset_32x32.png"))
    private val grassTerrainMap: MutableMap<TileType, TextureRegion> = hashMapOf()

    private val cowWalk: Texture = Texture(Gdx.files.internal("cow/cow_walk.png"))
    private val cowEat: Texture = Texture(Gdx.files.internal("cow/cow_eat.png"))
    private val cowShadow: Texture = Texture(Gdx.files.internal("cow/cow_shadow.png"))
    private val cowMap: MutableMap<Int, TextureRegion> = hashMapOf()

    init
    {
        initGrassTerrain()
        initCow()
    }

    private fun initGrassTerrain()
    {
        addTileRegion(0,0, TileType.GRASS0)
        addTileRegion(1,0, TileType.GRASS1)
        addTileRegion(2,0, TileType.GRASS2)
        addTileRegion(3,0, TileType.GRASS3)
        addTileRegion(5,0, TileType.FLOWER0)
        addTileRegion(7,0, TileType.FLOWER1)
        addTileRegion(0,2, TileType.LOWGRASS0)
        addTileRegion(1,2, TileType.TALLGRASS0)
    }

    private fun initCow()
    {
        var index = -1
        val w = cowWalk.width / TEXTURE_COW_SIZE
        val h = cowWalk.height / TEXTURE_COW_SIZE

        for(x in 0 until w)
        {
            for (y in 0 until h)
            {
                cowMap[index++] = TextureRegion(cowWalk, x * TEXTURE_COW_SIZE, y * TEXTURE_COW_SIZE, TEXTURE_COW_SIZE, TEXTURE_COW_SIZE)
            }
        }
    }

    private fun addTileRegion(x: Int, y: Int, tileType: TileType)
    {
        grassTerrainMap[tileType] = TextureRegion(grassTerrain32x32, x * TEXTURE_TILE_SIZE, y * TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE)
    }

    fun getGrassTerrainTextureRegion(tileType: TileType) : TextureRegion? = if(tileType != TileType.NONE) grassTerrainMap[tileType] else null

    fun getCowTexture(index: Int) : TextureRegion? = cowMap[index]

    fun dispose()
    {
        grassTerrain32x32.dispose()
    }
}