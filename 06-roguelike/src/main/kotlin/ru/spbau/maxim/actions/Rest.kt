package ru.spbau.maxim.actions

import ru.spbau.maxim.actions.Action
import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly

class Rest: Action {
    override fun execute(author: MobWithEffects, model: Model) {}

    override fun validate(author: MobWithEffects, model: ModelReadOnly): Boolean {
        return true
    }
}