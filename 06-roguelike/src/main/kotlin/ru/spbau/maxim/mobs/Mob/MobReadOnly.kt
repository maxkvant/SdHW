package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.model.Position

interface MobReadOnly: Cloneable {
    val attack: Int
    val defence: Int
    val type: MobType

    fun getHp(): Int

    fun isDead(): Boolean

    fun getPosition(): Position
}