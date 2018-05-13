package com.szczwany.cowsimulator.world

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.Settings.GAME_TILE_SIZE
import com.szczwany.cowsimulator.entity.Tile
import com.szczwany.cowsimulator.enums.TileType
import java.util.*

class Pasture(private val width: Int, private val height: Int)
{
    private val tilesData = mutableListOf<Tile>()

    init
    {
        generate()
    }

    private fun generate()
    {
        val random = Random()

        for(y in 0 until height)
        {
            for(x in 0 until width)
            {
                val index = x + y * width
                val tileType = random.nextInt(3)
                val tilePosition = Vector2(x.toFloat() * GAME_TILE_SIZE, y.toFloat() * GAME_TILE_SIZE)

                val tile = Tile(tilePosition, TileType.valueOf(tileType))

                tilesData.add(index, tile)
            }
        }
    }

    fun draw(spriteBatch: SpriteBatch)
    {
        for(tile in tilesData)
        {
            tile.draw(spriteBatch)
        }
    }
}
