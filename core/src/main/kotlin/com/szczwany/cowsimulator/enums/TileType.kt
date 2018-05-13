package com.szczwany.cowsimulator.enums

enum class TileType
{
    GRASS0;

    companion object
    {
        fun valueOf(value: Int): TileType = TileType.values().first { it.ordinal == value }
    }
}