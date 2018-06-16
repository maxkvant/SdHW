package ru.spbau.maxim.model

interface Model: ModelReadOnly {
    fun getMobs(): List<Mob>

    fun getMob(pos: Position): Mob?

    fun addMob(mob: Mob)

    fun removeMob(mob: Mob)

    fun getPlayer(): Mob

    fun updateMobPos(oldPos: Position)

    fun nextTurn()
}

