package ru.spbau.maxim.controller.actions

import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly

interface Action {
    fun execute(model: Model)

    fun validate(model: ModelReadOnly): Boolean
}