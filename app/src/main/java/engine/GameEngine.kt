package engine

import com.example.dartsscore.DartHit
import engine.GameState

interface GameEngine {

    fun addHit(hit: DartHit)

    fun undo()

    fun getState(): GameState
}