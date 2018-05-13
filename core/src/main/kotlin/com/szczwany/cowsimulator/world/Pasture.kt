package com.szczwany.cowsimulator.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.szczwany.cowsimulator.CowSimulatorGame
import com.szczwany.cowsimulator.Settings
import com.szczwany.cowsimulator.Settings.GAME_TILE_SIZE
import com.szczwany.cowsimulator.entity.Cow
import com.szczwany.cowsimulator.entity.Tile
import com.szczwany.cowsimulator.enums.ActionType
import com.szczwany.cowsimulator.enums.TileType
import java.util.*

class Pasture(private val width: Int, private val height: Int)
{
    private val tilesData = mutableListOf<Tile>()

    private val cow = Cow(Vector2(100F, 100F), CowSimulatorGame.assetLibrary.getCowTexture(0))

    init
    {
        generate()
    }

    private fun generate()
    {
        tilesData.clear()

        val random = Random()

        for(y in 0 until height)
        {
            for(x in 0 until width)
            {
                val index = x + y * width

                val tilePosition = Vector2(x.toFloat() * GAME_TILE_SIZE, y.toFloat() * GAME_TILE_SIZE)

                val basicTileType = TileType.GRASS0
                val actionTileType = if(random.nextBoolean()) TileType.valueOf(random.nextInt(2) + 7) else TileType.NONE

                val tile = Tile(tilePosition, basicTileType, actionTileType)

                tilesData.add(index, tile)
            }
        }
    }

    fun update(deltaTime: Float)
    {
        for (tile in tilesData)
        {
            tile.growTime = deltaTime
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.G))
        {
            generate()
        }

        val mouseX = Gdx.input.x
        val mouseY = Settings.WINDOW_HEIGHT - Gdx.input.y

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            tileAction(mouseX, mouseY, ActionType.EAT)
        }
        else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
        {
            tileAction(mouseX, mouseY, ActionType.PLANT)
        }

        cow.update(deltaTime)

        if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5))
        {
            tileAction(cow.position.x.toInt(), cow.position.y.toInt(), ActionType.EAT)
        }
    }

    fun draw(spriteBatch: SpriteBatch)
    {
        for(tile in tilesData)
        {
            tile.draw(spriteBatch)
        }

        cow.draw(spriteBatch)
    }

    private fun tileAction(mouseX: Int, mouseY: Int, actionType: ActionType)
    {
        val x = mouseX / GAME_TILE_SIZE.toInt()
        val y = mouseY / GAME_TILE_SIZE.toInt()

        val index = x + y * width

        if (actionType == ActionType.EAT)
        {
            tilesData[index].eatTallGrass()
        }
        else if(actionType == ActionType.PLANT)
        {
            tilesData[index].plantLowGrass()
        }
    }
}
