package ru.spbau.maxim.effects

import ru.spbau.maxim.mobs.Mob.EffectAbstract

class Helmet: EffectAbstract() {
    override val defence: Int
        get() = super.defence + 5
}