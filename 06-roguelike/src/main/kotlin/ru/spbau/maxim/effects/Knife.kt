package ru.spbau.maxim.effects

import ru.spbau.maxim.mobs.Mob.EffectAbstract

class Knife: EffectAbstract() {
    override val attack: Int
        get() = super.attack + 10
}