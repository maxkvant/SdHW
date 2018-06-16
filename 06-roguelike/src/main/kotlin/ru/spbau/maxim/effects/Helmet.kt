package ru.spbau.maxim.effects

import ru.spbau.maxim.effects.Effect

class Helmet: Effect() {
    override val defence: Int
        get() = super.defence + 5
}