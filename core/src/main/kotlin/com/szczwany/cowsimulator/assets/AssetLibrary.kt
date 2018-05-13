package com.szczwany.cowsimulator.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.szczwany.cowsimulator.Settings.TEXTURE_COW_SIZE
import com.szczwany.cowsimulator.Settings.TEXTURE_PLANT_SIZE
import com.szczwany.cowsimulator.Settings.TEXTURE_TILE_SIZE
import com.szczwany.cowsimulator.enums.PlantType
import com.szczwany.cowsimulator.enums.TileType

class AssetLibrary
{

    private val grassTerrain32x32: Texture = Texture(Gdx.files.internal("grass_tileset_32x32.png"))
    private val tileMap: MutableMap<TileType, TextureRegion> = hashMapOf()
    private val plantMap: MutableMap<PlantType, TextureRegion> = hashMapOf()

    private val cowWalk: Texture = Texture(Gdx.files.internal("cow/cow_walk.png"))
    private val cowEat: Texture = Texture(Gdx.files.internal("cow/cow_eat.png"))
    private val cowShadow: Texture = Texture(Gdx.files.internal("cow/cow_shadow.png"))
    private val cowMap: MutableMap<Int, TextureRegion> = hashMapOf()

    init
    {
        initTiles()
        initPlants()
        initCow()
    }

    private fun initTiles()
    {
        addTileRegion(0,0, TileType.GRASS0)
    }

    private fun initPlants()
    {
        addPlantRegion(1,0, PlantType.GRASS1)
        addPlantRegion(2,0, PlantType.GRASS2)
        addPlantRegion(3,0, PlantType.GRASS3)
        addPlantRegion(5,0, PlantType.FLOWER0)
        addPlantRegion(7,0, PlantType.FLOWER1)
        addPlantRegion(0,2, PlantType.LOWGRASS0)
        addPlantRegion(1,2, PlantType.TALLGRASS0)
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
        tileMap[tileType] = TextureRegion(grassTerrain32x32, x * TEXTURE_TILE_SIZE, y * TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE)
    }

    private fun addPlantRegion(x: Int, y: Int, plantType: PlantType)
    {
        plantMap[plantType] = TextureRegion(grassTerrain32x32, x * TEXTURE_PLANT_SIZE, y * TEXTURE_PLANT_SIZE, TEXTURE_PLANT_SIZE, TEXTURE_PLANT_SIZE)
    }

    fun getTileTextureRegion(tileType: TileType) : TextureRegion? = tileMap[tileType]
    fun getPlantTextureRegion(plantType: PlantType) : TextureRegion? = plantMap[plantType]
    fun getCowTexture(index: Int) : TextureRegion? = cowMap[index]

    fun dispose()
    {
        grassTerrain32x32.dispose()
    }
}