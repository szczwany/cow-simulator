package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.enums.EntityType

class Tile(position: Vector2, width: Float, height: Float, entityType: EntityType) : Entity(position, width, height, entityType)
{
    override fun update(deltaTime: Float)
    {}
}
