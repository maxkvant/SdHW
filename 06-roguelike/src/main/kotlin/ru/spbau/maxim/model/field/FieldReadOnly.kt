package ru.spbau.maxim.model.field

import ru.spbau.maxim.model.Position

interface FieldReadOnly<out T> {
    operator fun get(position: Position): T
    val rows: Int
    val columns: Int
}