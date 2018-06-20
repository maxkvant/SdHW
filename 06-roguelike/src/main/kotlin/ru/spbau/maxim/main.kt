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

fun main(args: Array<String>) {

    val field = generateField(40, 150)

    val (player, enemies) = generateMobs(field, 0.02)
    val model: Model = ModelImpl(field, player, enemies)

    val controller = GameController(model, object: GameView {})

    while (true) {
        controller.runGame {
            for (i in 0 until field.rows) {
                for (j in 0 until field.columns) {
                    val pos = Position(i, j)
                    val c = when {
                        pos == model.getPlayer().getPosition() -> '@'
                        model.getMob(pos) != null -> '&'
                        model.getCell(pos) == Cell.WALL -> '#'
                        model.getCell(pos) == Cell.SPACE -> '.'
                        model.getCell(pos) == Cell.ROUTE -> '.'
                        else -> ' '
                    }
                    print(c)
                }
                println()
            }
        }
    }
}