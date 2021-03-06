package com.szczwany.cowsimulator

object Settings
{
    const val WINDOW_WIDTH = 1280
    const val WINDOW_HEIGHT = 768

    const val TEXTURE_TILE_SIZE = 32
    const val GAME_TILE_SIZE = 64F

    const val TEXTURE_PLANT_SIZE = 32
    const val GAME_PLANT_SIZE = 64F

    // temp
    const val PASTURE_WIDTH = WINDOW_WIDTH / GAME_TILE_SIZE.toInt()
    const val PASTURE_HEIGHT = WINDOW_HEIGHT / GAME_TILE_SIZE.toInt()

    const val GRASS_GROW_TIME = 20F

    const val TEXTURE_COW_SIZE = 128
    const val GAME_COW_SIZE = 256F

    const val COW_BRAIN_TIME = 2F
}