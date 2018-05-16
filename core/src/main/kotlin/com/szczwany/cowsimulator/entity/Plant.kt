package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.Settings.GRASS_GROW_TIME
import com.szczwany.cowsimulator.enums.EntityType

class Plant(position: Vector2, width: Float, height: Float, entityType: EntityType) : Entity(position, width, height, entityType)
{
    private var growTime = 0F
    var remove = false

    val harvestable
        get() = entityType == EntityType.TALLGRASS0

    override fun update(deltaTime: Float)
    {
        if (!remove)
        {
            if (entityType == EntityType.LOWGRASS0 && growTime > GRASS_GROW_TIME)
            {
                growTallGrass()

                growTime = 0F
            }
        }
    }

    private fun growTallGrass()
    {
        entityType = EntityType.TALLGRASS0
    }

    fun getBounds() = Rectangle(position.x, position.y, width, height)
}
