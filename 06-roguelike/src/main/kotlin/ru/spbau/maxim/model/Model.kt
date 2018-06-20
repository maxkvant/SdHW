package ru.spbau.maxim.model

import ru.spbau.maxim.mobs.PlayerMobWithEffects

interface Model: ModelReadOnly {
    fun getMobs(): List<Mob>

    fun getMob(pos: Position): Mob?

    fun addMob(mob: Mob)

    fun removeMob(mob: Mob)

    fun getPlayer(): PlayerMobWithEffects

    fun updateMobPos(oldPos: Position)

    fun nextTurn(f: Model.() -> Unit)
}

