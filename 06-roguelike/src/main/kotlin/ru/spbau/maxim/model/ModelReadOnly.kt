package ru.spbau.maxim.model

import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.mobs.Mob.MobReadOnly
import ru.spbau.maxim.model.field.Cell
import ru.spbau.maxim.controller.effects.Effect

interface ModelReadOnly {
    fun getCell(position: Position): Cell

    fun getPlayerReadOnly(): MobReadOnly

    fun getEffects(mob: Mob): List<Effect>

    fun getMobReadOnly(position: Position): MobReadOnly?

    fun time(): Int

    fun finished(): Boolean

    fun getMobsReadOnly(): List<MobReadOnly>
}