package ru.spbau.maxim.model

import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.controller.effects.Effect

interface Model: ModelReadOnly {
    fun getMobs(): List<Mob>

    fun getMob(pos: Position): Mob?

    fun addEffect(mob: Mob, effect: Effect)

    fun setEffects(mob: Mob, effects: List<Effect>)

    fun addMob(mob: Mob)

    fun getPlayer(): Mob

    fun updateMobPos(mob: Mob)

    fun applyEffects(mob: Mob): Mob

    fun nextTurn()
}

