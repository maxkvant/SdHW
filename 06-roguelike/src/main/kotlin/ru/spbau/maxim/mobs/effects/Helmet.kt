package ru.spbau.maxim.mobs.effects

class Helmet: EffectAbstract() {
    override val defence: Int
        get() = super.defence + 5
}