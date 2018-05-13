package com.szczwany.cowsimulator.launcher

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.szczwany.cowsimulator.CowSimulatorGame
import com.szczwany.cowsimulator.Settings

fun main(args: Array<String>)
{
    val config = LwjglApplicationConfiguration()
    config.width = Settings.WINDOW_WIDTH
    config.height = Settings.WINDOW_HEIGHT

    LwjglApplication(CowSimulatorGame(), config)
}