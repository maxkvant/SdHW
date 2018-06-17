package ru.spbau.maxim.effects

import ru.spbau.maxim.actions.Action
import ru.spbau.maxim.actions.FreezeSibling
import ru.spbau.maxim.actions.HitAction
import ru.spbau.maxim.mobs.Mob.EffectAbstract
import ru.spbau.maxim.model.ModelReadOnly
import java.util.*

class Freezer: EffectAbstract() {
    override fun turn(env: ModelReadOnly): Action {
        val action = super.turn(env)
        return when (action) {
            is HitAction -> if (Random().nextBoolean()) action else FreezeSibling(this.getPosition(), action.victim)
            else -> action
        }
    }
}