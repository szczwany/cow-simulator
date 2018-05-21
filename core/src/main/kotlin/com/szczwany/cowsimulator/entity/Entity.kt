package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.CowSimulatorGame
import com.szczwany.cowsimulator.enums.EntityType

fun getTextureRegion(entityType: EntityType) = CowSimulatorGame.assetLibrary.getEntityTextureRegion(entityType)

abstract class Entity(protected var position: Vector2, protected val width: Float, protected val height: Float, protected var entityType: EntityType) : Comparable<Entity>
{
    val toRemove
        get() = entityType == EntityType.NONE

    protected open var entityTextureRegion: TextureRegion? = null
        get() = if (entityType != EntityType.ALIVE) getTextureRegion(entityType) else null

    open fun draw(spriteBatch: SpriteBatch)
    {
        if (!toRemove)
            spriteBatch.draw(entityTextureRegion, position.x, position.y, width, height)
    }

    abstract fun update(deltaTime: Float)

    override fun compareTo(other: Entity) : Int
    {
        if(other is Tile)
        {
            return 1
        }

        // TODO poprawic wyliczenie chowania sie krowy za krzakami
        if(this is Cow)
        {
            return 1
        }

        val tempY = other.position.y
        val compareY = position.y

        return if (tempY < compareY) -1 else if (tempY > compareY) 1 else 0
    }

    fun getCenter() = Vector2(position.x + width / 2, position.y + height / 2)
}