package ru.spbau.maxim.model.field

import ru.spbau.maxim.model.Position

/**
 * 2D immutable Grid, with left top conner (0, 0)
 */
interface FieldReadOnly<out T> {
    operator fun get(position: Position): T
    val rows: Int
    val columns: Int

    fun inside(position: Position): Boolean {
        return position.i in 0..(rows - 1) && position.j in 0..(columns - 1)
    }

    companion object {
        fun <T> of(arr: Array<Array<T>>): FieldReadOnly<T> {
            require(arr.isNotEmpty())
            for (i in arr.indices) {
                require(arr[i].size == arr[0].size)
            }

            return object : FieldReadOnly<T> {
                override fun get(position: Position): T = arr[position.i][position.j]
                override val rows = arr.size
                override val columns: Int = arr[0].size
            }
        }
    }
}