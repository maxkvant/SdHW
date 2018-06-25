package ru.spbau.maxim.model

import kotlin.math.abs

data class Position(val i: Int, val j: Int) {
    fun dist(other: Position): Int {
        return abs(i - other.i) + abs(j - other.j)
    }
}