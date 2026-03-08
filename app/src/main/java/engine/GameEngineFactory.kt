package com.example.dartsscore.engine

import com.example.dartsscore.data.entity.*

object GameEngineFactory {

    // GameType だけの既存 create
    fun create(gameType: GameType): GameEngine = when (gameType) {
        GameType.COUNT_UP -> CountUpEngine()
        GameType.INFINITE_COUNT_UP -> InfiniteCountUpEngine()
        GameType.ZERO_ONE_301 -> ZeroOneEngine(301)
        GameType.ZERO_ONE_501 -> ZeroOneEngine(501)
        GameType.ZERO_ONE_701 -> ZeroOneEngine(701)
        GameType.CRICKET -> CountUpEngine() // 仮
        GameType.HALFIT -> CountUpEngine() // 仮
    }

    // 01ゲーム用オーバーロード
    fun create(
        gameType: GameType,
        initialScore: Int,
        inRule: InRule,
        outRule: OutRule,
        bullRule: BullRule
    ): GameEngine {
        return when (gameType) {
            GameType.ZERO_ONE_301, GameType.ZERO_ONE_501, GameType.ZERO_ONE_701 -> {
                ZeroOneEngine(initialScore, ZeroOneRules(inRule, outRule, bullRule))
            }
            else -> create(gameType)
        }
    }
}