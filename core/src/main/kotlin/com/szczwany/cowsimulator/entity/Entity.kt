package com.szczwany.cowsimulator.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

abstract class Entity(protected open var position: Vector2) : Comparable<Entity>
{
    open fun draw(spriteBatch: SpriteBatch){}
    open fun update(deltaTime: Float){}

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