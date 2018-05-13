package com.szczwany.cowsimulator

import com.badlogic.gdx.Game
import com.szczwany.cowsimulator.assets.AssetLibrary
import com.szczwany.cowsimulator.screens.GameScreen

class CowSimulatorGame : Game()
{
    // Nie moze byc w companion object ?
    companion object
    {
        lateinit var assetLibrary: AssetLibrary
    }

    override fun create()
    {
        assetLibrary = AssetLibrary()

        setScreen(GameScreen())
    }

    override fun dispose()
    {
        super.dispose()

        assetLibrary.dispose()
    }
}
