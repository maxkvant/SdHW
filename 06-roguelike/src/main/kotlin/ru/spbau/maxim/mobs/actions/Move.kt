package ru.spbau.maxim.mobs.actions

import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.mobs.Mob.MobWithEffects
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.model.Position
import ru.spbau.maxim.model.field.Cell

/**
 * moves Mob to posTo
 */
data class Move(private val mob: Mob, private val posTo: Position): Action {
    override fun validate(author: MobWithEffects, model: ModelReadOnly): Boolean {
        return model.getField().inside(posTo)
                && model.getCell(posTo) != Cell.WALL
                && model.getMobReadOnly(posTo) == null
                && mob.getPosition().dist(posTo) == 1
    }

    override fun execute(author: MobWithEffects, model: Model) {
        mob.moveTo(posTo, model)
    }
}