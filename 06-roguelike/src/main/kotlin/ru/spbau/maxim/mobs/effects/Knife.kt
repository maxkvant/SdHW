package ru.spbau.maxim.mobs.effects

class Knife: EffectAbstract() {
    override val attack: Int
        get() = super.attack + 10
}