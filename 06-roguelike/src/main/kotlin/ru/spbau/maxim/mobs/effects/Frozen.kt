package ru.spbau.maxim.mobs.effects

import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.actions.Rest
import ru.spbau.maxim.model.Model

/**
 * Freezes: substitutes every mob action to Rest Action and mobCore does nothing
 */
class Frozen: EffectImpl() {
    val timeActive = 5
    override fun turn(env: Model): Action = Rest
}