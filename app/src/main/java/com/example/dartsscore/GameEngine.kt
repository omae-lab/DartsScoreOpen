package com.example.dartsscore.engine

import com.example.dartsscore.DartHit

interface GameEngine {

    fun addHit(hit: DartHit)

    fun undo()

    fun getState(): GameState
}