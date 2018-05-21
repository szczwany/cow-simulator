package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.Settings.GRASS_GROW_TIME
import com.szczwany.cowsimulator.enums.EntityType

class Plant(position: Vector2, width: Float, height: Float, entityType: EntityType, val foodQuantity: Float, private var growTime: Float = 0F) : Entity(position, width, height, entityType)
{
    val isHarvestable
        get() = entityType == EntityType.TALLGRASS0

    override fun update(deltaTime: Float)
    {
        if (entityType == EntityType.LOWGRASS0 && growTime > GRASS_GROW_TIME)
        {
            growTallGrass()

            growTime = 0F
        }
    }

    private fun growTallGrass()
    {
        entityType = EntityType.TALLGRASS0
    }

    fun eatTallGrass()
    {
        if(isHarvestable)
        {
            entityType = EntityType.NONE
        }
    }
}
