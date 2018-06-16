package ru.spbau.maxim.model

interface FieldReadOnly<out T> {
    operator fun get(i: Int, j: Int): T
    val height: Int
    val width: Int
}