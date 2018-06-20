package ru.spbau.maxim.mobs.actions

import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.model.ModelReadOnly

/**
 * HitAction: Action to attack victim on purpose
 */
interface HitAction: Action {
    val victim: MobWithEffects

    override fun validate(author: MobWithEffects, model: ModelReadOnly): Boolean {
        return !victim.isDead()
    }
}