package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.Settings.GRASS_GROW_TIME
import com.szczwany.cowsimulator.enums.EntityType

class Plant(position: Vector2, width: Float, height: Float, entityType: EntityType) : Entity(position, width, height, entityType)
{
    var growTime = 0F
        set(value)
        {
            if(entityType == EntityType.LOWGRASS0)
            {
                if(field > GRASS_GROW_TIME)
                {
                    growTallGrass()

                    field = 0F
                }
                else
                {
                    field += value
                }
            }
        }

    val harvestable
        get() = entityType == EntityType.TALLGRASS0

    override fun update(deltaTime: Float)
    {}

    override fun draw(spriteBatch: SpriteBatch)
    {
        if(entityTextureRegion != null)
        {
            super.draw(spriteBatch)
        }
    }

    private fun growTallGrass()
    {
        if(entityType == EntityType.LOWGRASS0)
        {
            entityType = EntityType.TALLGRASS0
        }
    }

    fun getBounds() = Rectangle(position.x, position.y, width, height)
}
