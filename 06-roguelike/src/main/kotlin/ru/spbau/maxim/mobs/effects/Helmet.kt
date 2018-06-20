package ru.spbau.maxim.mobs.effects

/**
 * Helmet effect: increases Mob's defence
 */
class Helmet: EffectAbstract() {
    override val defence: Int
        get() = super.defence + 3
}