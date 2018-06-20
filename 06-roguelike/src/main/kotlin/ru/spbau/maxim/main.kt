package ru.spbau.maxim

import ru.spbau.maxim.controller.GameController
import ru.spbau.maxim.model.generators.generateField
import ru.spbau.maxim.model.generators.generateMobs
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.impl.ModelImpl
import ru.spbau.maxim.view.GameViewIml

fun main(args: Array<String>) {

    val field = generateField(200, 200)

    val modelGenerator: () -> Model = {
        val (player, enemies) = generateMobs(field, 0.02)
        ModelImpl(field, player, enemies)
    }
        val view = GameViewIml(60, 40)

    GameController(modelGenerator, view)
}