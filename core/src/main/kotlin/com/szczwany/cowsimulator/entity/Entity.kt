package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.CowSimulatorGame
import com.szczwany.cowsimulator.enums.EntityType

fun getTextureRegion(entityType: EntityType) = CowSimulatorGame.assetLibrary.getEntityTextureRegion(entityType)

abstract class Entity(protected open var position: Vector2, protected val width: Float, protected val height: Float, protected var entityType: EntityType) : Comparable<Entity>
{
    protected open var entityTextureRegion: TextureRegion? = null
        get() = if(entityType != EntityType.ALIVE) getTextureRegion(entityType) else null

    open fun draw(spriteBatch: SpriteBatch)
    {
        spriteBatch.draw(entityTextureRegion, position.x, position.y, width, height)
    }

    abstract fun update(deltaTime: Float)

    override fun compareTo(other: Entity) : Int
    {
        if(other is Tile)
        {
            return 1
        }

        val tempY = other.position.y
        val compareY = position.y

        return if (tempY < compareY) -1 else if (tempY > compareY) 1 else 0
    }
}