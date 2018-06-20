package ru.spbau.maxim.mobs.actions

import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly

interface Action {
    fun execute(author: MobWithEffects, model: Model)

    fun validate(author: MobWithEffects, model: ModelReadOnly): Boolean

    fun executeIfValid(author: MobWithEffects, model: Model) {
        if (validate(author, model)) {
            execute(author, model)
        }
    }
}