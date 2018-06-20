package ru.spbau.maxim.mobs.effects

import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.actions.FreezeSibling
import ru.spbau.maxim.mobs.actions.HitAction
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