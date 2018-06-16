package ru.spbau.maxim.effects

import ru.spbau.maxim.effects.Effect

class Knife: Effect() {
    override val attack: Int
        get() = super.attack + 10
}