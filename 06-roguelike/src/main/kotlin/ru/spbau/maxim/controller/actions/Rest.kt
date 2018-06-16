package ru.spbau.maxim.controller.actions

import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly

class Rest: Action {
    override fun execute(model: Model) {}

    override fun validate(model: ModelReadOnly): Boolean {
        return true
    }
}