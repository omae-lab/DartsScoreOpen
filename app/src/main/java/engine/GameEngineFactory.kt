package com.example.dartsscore.engine

import com.example.dartsscore.data.entity.GameType

object GameEngineFactory {

    fun create(gameType: GameType): GameEngine {

        return when (gameType) {

            GameType.COUNT_UP ->
                CountUpEngine()

            GameType.INFINITE_COUNT_UP ->
                InfiniteCountUpEngine()

            GameType.ZERO_ONE_301 ->
                ZeroOneEngine(301)

            GameType.ZERO_ONE_501 ->
                ZeroOneEngine(501)

            GameType.ZERO_ONE_701 ->
                ZeroOneEngine(701)

            GameType.CRICKET ->
                CountUpEngine() // 仮

            GameType.HALFIT ->
                CountUpEngine() // 仮
        }
    }
}