package ru.spbau.maxim.mobs.effects

import ru.spbau.maxim.mobs.actions.Rest
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.mobs.actions.Action

class Frozen: EffectAbstract() {
    val timeToLive = 5
    override fun turn(env: ModelReadOnly): Action = Rest()
}