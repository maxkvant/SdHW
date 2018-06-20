package ru.spbau.maxim.mobs.effects

/**
 * Helmet effect: increases mobCore's defence
 */
class Helmet: EffectAbstract() {
    override val defence: Int
        get() = super.defence + 3
}