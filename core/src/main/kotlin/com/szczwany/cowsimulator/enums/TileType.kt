package com.szczwany.cowsimulator.enums

enum class TileType
{
    GRASS0,
    GRASS1,
    GRASS2,
    GRASS3,
    GRASS4,
    GRASS5,
    GRASS6,
    GRASS7,
    GRASS8,
    WATER;

    companion object
    {
        fun valueOf(value: Int): TileType = TileType.values().first { it.ordinal == value }
    }
}