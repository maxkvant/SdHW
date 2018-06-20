package ru.spbau.maxim.model

import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.mobs.PlayerMobWithEffects

interface Model: ModelReadOnly {
    fun getMobs(): List<MobWithEffects>

    fun getMob(pos: Position): MobWithEffects?

    fun addMob(mobWithEffects: MobWithEffects)

    fun removeMob(mobWithEffects: MobWithEffects)

    fun getPlayer(): PlayerMobWithEffects

    fun updateMobPos(oldPos: Position)

    fun nextTurn(f: Model.() -> Unit)
}

