package com.szczwany.cowsimulator.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.szczwany.cowsimulator.Settings.TEXTURE_COW_SIZE
import com.szczwany.cowsimulator.Settings.TEXTURE_TILE_SIZE
import com.szczwany.cowsimulator.enums.EntityType

class AssetLibrary
{

    private val grassTerrain32x32: Texture = Texture(Gdx.files.internal("grass_tileset_32x32.png"))
    private val entityMap: MutableMap<EntityType, TextureRegion> = hashMapOf()

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
        addEntityTextureRegion(0,0, EntityType.GRASS0)
        addEntityTextureRegion(1,0, EntityType.GRASS1)
        addEntityTextureRegion(2,0, EntityType.GRASS2)
        addEntityTextureRegion(3,0, EntityType.GRASS3)
        addEntityTextureRegion(5,0, EntityType.FLOWER0)
        addEntityTextureRegion(7,0, EntityType.FLOWER1)
        addEntityTextureRegion(0,2, EntityType.LOWGRASS0)
        addEntityTextureRegion(1,2, EntityType.TALLGRASS0)
    }

    private fun initCow()
    {
        var index = 0
        var w = cowWalk.width / TEXTURE_COW_SIZE
        var h = cowWalk.height / TEXTURE_COW_SIZE

        for(y in 0 until h)
        {
            for (x in 0 until w)
            {
                cowMap[index] = TextureRegion(cowWalk, x * TEXTURE_COW_SIZE, y * TEXTURE_COW_SIZE, TEXTURE_COW_SIZE, TEXTURE_COW_SIZE)
                index++
            }
        }

        for(y in 0 until h)
        {
            for (x in 0 until w)
            {
                cowMap[index] = TextureRegion(cowEat, x * TEXTURE_COW_SIZE, y * TEXTURE_COW_SIZE, TEXTURE_COW_SIZE, TEXTURE_COW_SIZE)
                index++
            }
        }

        w = cowShadow.width / TEXTURE_COW_SIZE
        h = cowShadow.height / TEXTURE_COW_SIZE

        for(y in 0 until h)
        {
            for (x in 0 until w)
            {
                cowMap[index] = TextureRegion(cowShadow, x * TEXTURE_COW_SIZE, y * TEXTURE_COW_SIZE, TEXTURE_COW_SIZE, TEXTURE_COW_SIZE)
                index++
            }
        }
    }

    private fun addEntityTextureRegion(x: Int, y: Int, entityType: EntityType)
    {
        entityMap[entityType] = TextureRegion(grassTerrain32x32, x * TEXTURE_TILE_SIZE, y * TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE)
    }

    fun getEntityTextureRegion(entityType: EntityType) : TextureRegion? = entityMap[entityType]

    fun getCowTexture(index: Int) : TextureRegion? = cowMap[index]

    fun dispose()
    {
        grassTerrain32x32.dispose()
        cowWalk.dispose()
        cowEat.dispose()
        cowShadow.dispose()
    }
}