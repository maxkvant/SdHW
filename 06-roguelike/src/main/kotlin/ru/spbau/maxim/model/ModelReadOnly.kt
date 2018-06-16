package ru.spbau.maxim.model

import ru.spbau.maxim.mobs.Mob.MobReadOnly
import ru.spbau.maxim.model.field.Cell

interface ModelReadOnly {
    fun getCell(position: Position): Cell

    fun getPlayerReadOnly(): MobReadOnly

    fun getMobReadOnly(position: Position): MobReadOnly?

    fun time(): Int

    fun finished(): Boolean

    fun getMobsReadOnly(): List<MobReadOnly>
}