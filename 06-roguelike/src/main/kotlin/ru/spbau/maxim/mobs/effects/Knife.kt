package ru.spbau.maxim.mobs.effects

/**
 * Knife Effect: Increases mobCore's attack
 */
class Knife: EffectImpl() {
    override val attack: Int
        get() = super.attack + 8
}