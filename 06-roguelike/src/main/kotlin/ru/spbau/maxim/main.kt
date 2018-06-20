package ru.spbau.maxim

import ru.spbau.maxim.controller.GameController
import ru.spbau.maxim.mobs.effects.StorageEffect
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.generators.generateField
import ru.spbau.maxim.model.generators.generateMobs
import ru.spbau.maxim.mobs.Mob.MobDecoratorCombiner
import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.mobs.MobFactory
import ru.spbau.maxim.mobs.artifacts.PlayerArtifactStorage
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.impl.ModelImpl
import ru.spbau.maxim.view.GameView
import ru.spbau.maxim.view.GameViewIml

fun main(args: Array<String>) {

    val field = generateField(200, 200)

    val (player, enemies) = generateMobs(field, 0.02)
    val model: Model = ModelImpl(field, player, enemies)
    val view = GameViewIml(40, 60)

    GameController(model, view)
}