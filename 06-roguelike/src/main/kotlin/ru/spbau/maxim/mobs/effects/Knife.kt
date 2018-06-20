package ru.spbau.maxim.mobs.effects

/**
 * Knife Effect: Increases mobCore's attack
 */
class Knife: EffectAbstract() {
    override val attack: Int
        get() = super.attack + 10
}