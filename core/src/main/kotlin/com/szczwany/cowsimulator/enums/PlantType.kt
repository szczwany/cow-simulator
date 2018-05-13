package com.szczwany.cowsimulator.enums

enum class PlantType
{
    NONE,
    GRASS1,
    GRASS2,
    GRASS3,
    FLOWER0,
    FLOWER1,
    LOWGRASS0,
    TALLGRASS0;

    companion object
    {
        fun valueOf(value: Int): PlantType = PlantType.values().first { it.ordinal == value }
    }
}