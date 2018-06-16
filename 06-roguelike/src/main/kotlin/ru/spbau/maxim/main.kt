package ru.spbau.maxim

import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.generators.generateField

fun main(args: Array<String>) {
    val field = generateField()
    for (i in 0 until field.height) {
        for (j in 0 until field.width) {
            val c: Char = when (field[Position(i, j)]) {
                Cell.WALL -> '#'
                Cell.SPACE -> '.'
                Cell.ROUTE -> '.'
            }
            print(c)
        }
        println()
    }
}