package com.example.dartsscore.data.entity

enum class InRule {
    OPEN,
    DOUBLE,
    MASTER
}

enum class OutRule {
    OPEN,
    DOUBLE,
    MASTER
}

enum class BullRule {
    SINGLE_BULL_25,   // 25 / 50
    DOUBLE_BULL_50    // 50 only
}

data class ZeroOneRules(

    val inRule: InRule = InRule.OPEN,

    val outRule: OutRule = OutRule.OPEN,

    val bullRule: BullRule = BullRule.DOUBLE_BULL_50
)