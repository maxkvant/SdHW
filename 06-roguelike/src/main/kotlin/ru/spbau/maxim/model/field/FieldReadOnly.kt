package ru.spbau.maxim.model.field

import ru.spbau.maxim.model.Position

interface FieldReadOnly<out T> {
    operator fun get(position: Position): T
    val rows: Int
    val columns: Int

    fun inside(position: Position): Boolean {
        return position.i in 0..(rows - 1) && position.j in 0..(columns - 1)
    }
}