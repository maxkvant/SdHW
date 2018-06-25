package ru.spbau.maxim.model.field

/**
 * Cell game field
 * SPACE, ROUTE: you can move move that cell if it is free
 * WALL: nobody can move here
 */
enum class Cell {
    WALL, SPACE, ROUTE
}