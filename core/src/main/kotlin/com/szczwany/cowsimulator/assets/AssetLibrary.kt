package com.szczwany.cowsimulator.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
import com.szczwany.cowsimulator.Settings.TEXTURE_COW_SIZE
import com.szczwany.cowsimulator.Settings.TEXTURE_TILE_SIZE
import com.szczwany.cowsimulator.enums.EntityType

class AssetLibrary
{
    private val eatRate = .2F
    private val walkRate = .2F

    private val grassTerrain32x32: Texture = Texture(Gdx.files.internal("grass_tileset_32x32.png"))
    private val entityMap: MutableMap<EntityType, TextureRegion> = hashMapOf()

    private val cowWalkTexture: Texture = Texture(Gdx.files.internal("cow/cow_walk.png"))
    private val cowEat: Texture = Texture(Gdx.files.internal("cow/cow_eat.png"))
    private val cowShadow: Texture = Texture(Gdx.files.internal("cow/cow_shadow.png"))

    val cowMessageFont = BitmapFont(Gdx.files.internal("fonts/gabriola.fnt"))
    val cowMessageCloud = Texture(Gdx.files.internal("message_cloud.png"))

    private val cowWalkUpRegions = getFramesFromTexture(cowWalkTexture, TEXTURE_COW_SIZE, 0)
    private val cowWalkLeftRegions = getFramesFromTexture(cowWalkTexture, TEXTURE_COW_SIZE, 1)
    private val cowWalkDownRegions = getFramesFromTexture(cowWalkTexture, TEXTURE_COW_SIZE, 2)
    private val cowWalkRightRegions = getFramesFromTexture(cowWalkTexture, TEXTURE_COW_SIZE, 3)
    private val cowEatUpRegions = getFramesFromTexture(cowEat, TEXTURE_COW_SIZE, 0)
    private val cowEatLeftRegions = getFramesFromTexture(cowEat, TEXTURE_COW_SIZE, 1)
    private val cowEatDownRegions = getFramesFromTexture(cowEat, TEXTURE_COW_SIZE, 2)
    private val cowEatRightRegions = getFramesFromTexture(cowEat, TEXTURE_COW_SIZE, 3)

    val cowShadowRegions = getFramesFromTexture(cowShadow, TEXTURE_COW_SIZE, 0, true)

    val cowAnimations = arrayOf(
            Animation(walkRate, cowWalkUpRegions),
            Animation(walkRate, cowWalkLeftRegions),
            Animation(walkRate, cowWalkDownRegions),
            Animation(walkRate, cowWalkRightRegions),
            Animation(eatRate, cowEatUpRegions),
            Animation(eatRate, cowEatLeftRegions),
            Animation(eatRate, cowEatDownRegions),
            Animation(eatRate, cowEatRightRegions))

    init
    {
        initGrassTerrain()
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

    private fun getFramesFromTexture(texture: Texture, regionSize: Int, row: Int, flip: Boolean = false): Array<TextureRegion>
    {
        val frames = Array<TextureRegion>()
        val size = if(flip) texture.height / regionSize else texture.width / regionSize

        for(column in 0 until size)
        {
            if(flip)
            {
                frames.add(TextureRegion(texture, row * regionSize, column * regionSize, regionSize, regionSize))
            }
            else
            {
                frames.add(TextureRegion(texture, column * regionSize, row * regionSize, regionSize, regionSize))
            }
        }

        return frames
    }

    private fun addEntityTextureRegion(x: Int, y: Int, entityType: EntityType)
    {
        entityMap[entityType] = TextureRegion(grassTerrain32x32, x * TEXTURE_TILE_SIZE, y * TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE, TEXTURE_TILE_SIZE)
    }

    fun getEntityTextureRegion(entityType: EntityType) : TextureRegion? = entityMap[entityType]

    fun dispose()
    {
        grassTerrain32x32.dispose()
        cowWalkTexture.dispose()
        cowEat.dispose()
        cowShadow.dispose()
        cowMessageFont.dispose()
    }
}