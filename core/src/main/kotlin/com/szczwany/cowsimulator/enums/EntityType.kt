package com.szczwany.cowsimulator.enums

enum class EntityType
{
    NONE,
    ALIVE,
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
        fun valueOf(value: Int): EntityType = EntityType.values().first { it.ordinal == value }
    }
}