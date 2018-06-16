package ru.spbau.maxim.controller.actions

import ru.spbau.maxim.controller.effects.Frozen
import ru.spbau.maxim.mobs.Mob.Mob
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import java.util.*

class FreezeSibling(cause: Mob, private val victim: Mob): Action {
    private val causeOldPos = cause.getPosition()
    private val victimOldPos = victim.getPosition()

    override fun validate(model: ModelReadOnly): Boolean {
        return causeOldPos.dist(victimOldPos) == 1
    }

    override fun execute(model: Model) {
        if (model.getEffects(victim).any { it is Frozen }) {
            if (Random().nextBoolean()) {
                model.addEffect(victim, Frozen(model.time()))
            }
        }
    }
}