package ru.spbau.maxim.actions

import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.model.Mob
import ru.spbau.maxim.model.ModelReadOnly

interface HitAction: Action {
    val victim: Mob

    override fun validate(author: MobWithEffects, model: ModelReadOnly): Boolean {
        return !victim.isDead()
    }
}