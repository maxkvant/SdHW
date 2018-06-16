package ru.spbau.maxim.controller.actions

import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell

class Move(private val mob: Mob, private val posTo: Position): Action {
    override fun validate(model: ModelReadOnly): Boolean {
        return model.getCell(posTo) != Cell.WALL
                && model.getMobReadOnly(posTo) != null
                && mob.getPosition().dist(posTo) == 1
    }

    override fun execute(model: Model) {
        mob.moveTo(posTo, model)
    }
}