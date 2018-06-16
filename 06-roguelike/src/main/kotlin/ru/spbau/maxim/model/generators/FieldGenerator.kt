package ru.spbau.maxim.model.generators

import com.github.czyzby.noise4j.map.Grid
import ru.spbau.maxim.model.Cell
import ru.spbau.maxim.model.FieldReadOnly
import com.github.czyzby.noise4j.map.generator.room.dungeon.DungeonGenerator
import java.lang.Math.abs

fun generateField(): FieldReadOnly<Cell> {
    val n = 512
    val m = n
    val arr = Array(n, {Array<Cell>(m, { Cell.WALL})})

    val grid = Grid(512)
    val dungeonGenerator = DungeonGenerator()
    dungeonGenerator.roomGenerationAttempts = 500
    dungeonGenerator.maxRoomSize = 75
    dungeonGenerator.tolerance = 10 // Max difference between width and height.
    dungeonGenerator.minRoomSize = 9
    dungeonGenerator.generate(grid)

    val eps = 1e-3

    for (i in arr.indices) {
        for (j in arr.indices) {
            val color = grid[i, j]
            arr[i][j] = when {
                abs(color - 0.5) < eps -> Cell.SPACE
                abs(color - 0.0) < eps -> Cell.ROUTE
                else -> Cell.WALL
            }
        }
        println()
    }

    return object : FieldReadOnly<Cell> {
        override fun get(i: Int, j: Int): Cell = arr[i][j]
        override val width = n
        override val height: Int = m
    }
}