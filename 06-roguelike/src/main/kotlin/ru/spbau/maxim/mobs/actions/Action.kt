package ru.spbau.maxim.mobs.actions

import ru.spbau.maxim.mobs.mobCore.MobWithEffects
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly

/**
 * Action that mob want to do
 */
interface Action {
    /**
     * run Action
     */
    fun execute(author: MobWithEffects, model: Model)

    /**
     * checks is Action valid
     */
    fun validate(author: MobWithEffects, model: ModelReadOnly): Boolean

    /**
     * runs if it valid
     */
    fun executeIfValid(author: MobWithEffects, model: Model) {
        if (validate(author, model)) {
            execute(author, model)
        }
    }
}