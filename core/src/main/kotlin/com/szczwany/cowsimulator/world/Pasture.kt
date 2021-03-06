package com.szczwany.cowsimulator.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.Settings.GAME_COW_SIZE
import com.szczwany.cowsimulator.Settings.GAME_PLANT_SIZE
import com.szczwany.cowsimulator.Settings.GAME_TILE_SIZE
import com.szczwany.cowsimulator.Settings.WINDOW_HEIGHT
import com.szczwany.cowsimulator.Settings.WINDOW_WIDTH
import com.szczwany.cowsimulator.astar.AStar
import com.szczwany.cowsimulator.astar.Node
import com.szczwany.cowsimulator.entity.Cow
import com.szczwany.cowsimulator.entity.Entity
import com.szczwany.cowsimulator.entity.Plant
import com.szczwany.cowsimulator.entity.Tile
import com.szczwany.cowsimulator.enums.EntityType
import java.util.*

class Pasture(private val width: Int, private val height: Int)
{
    private val entityList = mutableListOf<Entity>()

    private val cow = Cow(Vector2(WINDOW_WIDTH / 2F, WINDOW_HEIGHT / 2F),
            GAME_COW_SIZE, GAME_COW_SIZE, this)

    val nodes = generateAStar(width, height)

    init
    {
        generate()
    }

    private fun generateAStar(width: Int, height: Int): MutableList<Node>
    {
        val nodes = mutableListOf<Node>()

        for (y in 0 until height)
        {
            for (x in 0 until width)
            {
                val node = Node(x, y, true)

                nodes.add(node)
            }
        }

        return nodes
    }

    private fun generate()
    {
        entityList.clear()

        // init grass
        for(y in 0 until height)
        {
            for(x in 0 until width)
            {
                val entityPosition = Vector2(x * GAME_TILE_SIZE, y * GAME_TILE_SIZE)
                val tile = Tile(entityPosition, GAME_TILE_SIZE, GAME_TILE_SIZE, EntityType.GRASS0)

                entityList.add(tile)
            }
        }

        // init entities
        val random = Random()

        for(y in 0 until height)
        {
            for(x in 0 until width)
            {
                if(random.nextBoolean())
                {
                    continue
                }

                val entityPosition = Vector2(x * GAME_PLANT_SIZE, y * GAME_PLANT_SIZE)
                val entityType: EntityType
                val foodQuantity: Float

                if (random.nextInt(100) > 20)
                {
                    entityType = EntityType.valueOf(random.nextInt(2) + 8)
                    foodQuantity = 20F
                }
                else
                {
                    entityType = EntityType.valueOf(random.nextInt(2) + 6)
                    foodQuantity = 0F

                }

                val plant = Plant(entityPosition, GAME_PLANT_SIZE, GAME_PLANT_SIZE, entityType, foodQuantity)

                entityList.add(plant)
            }
        }

        entityList.add(cow)
    }

    fun update(deltaTime: Float)
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.G))
        {
            generate()
        }

        for (entity in entityList)
        {
            entity.update(deltaTime)
        }

        clearOldEntities()
    }

    fun draw(spriteBatch: SpriteBatch)
    {
        entityList.sort()

        for(entity in entityList)
        {
            entity.draw(spriteBatch)
        }

        cow.drawCowMessage(spriteBatch)
    }

    fun findHarvestablePlant() : Plant
    {
        // todo co jesli nie ma juz duzej trawy? -> GAME OVER JESLI NULL (bedzie trzeba sadzic, zeby miala co jesc)

        val harvestablePlants = mutableListOf<Plant>()

        for(entity in entityList)
        {
            if(entity is Plant && entity.isHarvestable)
            {
                harvestablePlants.add(entity)
            }
        }

        val index = random.nextInt(harvestablePlants.size)

        return harvestablePlants[index]
    }

    private fun clearOldEntities()
    {
        val entitiesToRemove = mutableListOf<Entity>()

        for (entity in entityList)
        {
            if (entity.toRemove)
            {
                entitiesToRemove.add(entity)
            }
        }

        entityList.removeAll(entitiesToRemove)
    }
}
