package ru.spbau.maxim

import ru.spbau.maxim.effects.StorageEffect
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.generators.generateField
import ru.spbau.maxim.generators.generateMobs
import ru.spbau.maxim.mobs.Mob.MobDecoratorCombiner
import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.mobs.SleepingMob
import ru.spbau.maxim.model.Artifacts.EmptyArtifactStorage
import ru.spbau.maxim.model.Artifacts.OneArtifactStorage
import ru.spbau.maxim.model.Artifacts.PlayerArtifactStorage
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.impl.ModelImpl
import java.lang.Thread.sleep

fun main(args: Array<String>) {

    while (true) {
        val field = generateField(40, 150)
        val (player, enemies) = generateMobs(field, 0.01, { pos ->
            val mob = object : SleepingMob(pos) {
                override val artifactStorage = PlayerArtifactStorage()
            }
            val mobWithEffects: MobWithEffects = MobDecoratorCombiner(mob)
            mobWithEffects.addDecorator(StorageEffect())
            return@generateMobs mobWithEffects
        })
        val model: Model = ModelImpl(field, player, enemies)

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
        sleep(1000)
    }
}