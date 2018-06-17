package ru.spbau.maxim.effects

import ru.spbau.maxim.actions.Rest
import ru.spbau.maxim.model.ModelReadOnly
import ru.spbau.maxim.actions.Action
import ru.spbau.maxim.mobs.Mob.EffectAbstract

class Frozen: EffectAbstract() {
    val timeToLive = 5
    override fun turn(env: ModelReadOnly): Action = Rest()
}