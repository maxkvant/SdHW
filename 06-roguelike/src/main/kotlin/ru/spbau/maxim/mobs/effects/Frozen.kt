package ru.spbau.maxim.mobs.effects

import ru.spbau.maxim.mobs.actions.Rest
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.model.Model

/**
 * Freezes: substitutes every mob action to Rest Action and Mob does nothing
 */
class Frozen: EffectAbstract() {
    val timeToLive = 5
    override fun turn(env: Model): Action = Rest()
}