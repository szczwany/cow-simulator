package com.szczwany.cowsimulator.enums

enum class TileType
{
    NONE,
    GRASS0,
    GRASS1,
    GRASS2,
    GRASS3,
    FLOWER0,
    FLOWER1,
    LOWGRASS0,
    TALLGRASS0;

    companion object
    {
        fun valueOf(value: Int): TileType = TileType.values().first { it.ordinal == value }
    }
}