package com.szczwany.cowsimulator.screens

import com.badlogic.gdx.Screen

abstract class BaseScreen : Screen
{
    override fun pause()
    {

    }

    override fun resume()
    {

    }

    override fun resize(p0: Int, p1: Int)
    {

    }

    override fun hide()
    {
        dispose()
    }
}