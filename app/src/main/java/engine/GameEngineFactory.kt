package com.example.dartsscore.engine

import com.example.dartsscore.data.entity.GameType
//import com.example.dartsscore.model.GameType

object GameEngineFactory {

    fun create(gameType: GameType): GameEngine {
        return when (gameType) {

            GameType.COUNT_UP ->
                CountUpEngine(maxRounds = 8)

            GameType.INFINITE_COUNT_UP ->
                CountUpEngine(maxRounds = null)


            GameType.ZERO_ONE_301 ->
                ZeroOneEngine(startScore = 301)

            GameType.ZERO_ONE_501 ->
                ZeroOneEngine(startScore = 501)

            GameType.ZERO_ONE_701 ->
                ZeroOneEngine(startScore = 701)

            GameType.CRICKET ->
                CricketEngine()

            GameType.HALFIT ->
                HalfitEngine()


        }
    }
}