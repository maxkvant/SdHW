package ru.spbau.maxim.model.generators

import com.github.czyzby.noise4j.map.Grid
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.field.FieldReadOnly
import com.github.czyzby.noise4j.map.generator.room.dungeon.DungeonGenerator
import ru.spbau.maxim.model.Position
import java.lang.Math.abs

fun generateField(n: Int, m: Int): FieldReadOnly<Cell> {
    val arr = Array(n, {Array<Cell>(m, { Cell.WALL})})

    val grid = Grid(n, m)
    val dungeonGenerator = DungeonGenerator()
    dungeonGenerator.roomGenerationAttempts = 500
    dungeonGenerator.maxRoomSize = 27
    dungeonGenerator.tolerance = 10 // Max difference between columns and rows.
    dungeonGenerator.minRoomSize = 9
    dungeonGenerator.generate(grid)

    val eps = 1e-3

    for (i in arr.indices) {
        for (j in arr[i].indices) {
            val color = grid[i, j]
            arr[i][j] = when {
                abs(color - 0.5) < eps -> Cell.SPACE
                abs(color - 0.0) < eps -> Cell.ROUTE
                else -> Cell.WALL
            }
        }
    }

    return object : FieldReadOnly<Cell> {
        override fun get(position: Position): Cell = arr[position.i][position.j]
        override val rows = n
        override val columns: Int = m
    }
}