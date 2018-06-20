package ru.spbau.maxim.mobs.actions

import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.model.ModelReadOnly

interface HitAction: Action {
    val victim: MobWithEffects

    override fun validate(author: MobWithEffects, model: ModelReadOnly): Boolean {
        return !victim.isDead()
    }
}