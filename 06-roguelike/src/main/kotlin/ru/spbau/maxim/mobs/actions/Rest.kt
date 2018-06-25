package ru.spbau.maxim.mobs.actions

import ru.spbau.maxim.mobs.mobCore.MobWithEffects
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly

/**
 * Action to rest (do nothing)
 */
object Rest: Action {
    override fun execute(author: MobWithEffects, model: Model) {}

    override fun validate(author: MobWithEffects, model: ModelReadOnly): Boolean {
        return true
    }
}