package com.szczwany.cowsimulator

import com.badlogic.gdx.Game
import com.szczwany.cowsimulator.assets.AssetsManager
import com.szczwany.cowsimulator.screens.GameScreen

class CowSimulatorGame : Game()
{
    // Nie moze byc w companion object ?
    companion object
    {
        lateinit var assetsManager: AssetsManager
    }

    override fun create()
    {
        assetsManager = AssetsManager()

        setScreen(GameScreen())
    }

    override fun dispose()
    {
        super.dispose()

        assetsManager.dispose()
    }
}
