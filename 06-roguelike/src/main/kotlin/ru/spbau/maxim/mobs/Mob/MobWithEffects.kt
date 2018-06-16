package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.model.ModelReadOnly

interface MobWithEffects: Mob {
    fun addDecorator(decorator: MobDecoratorMutable, timeToLive: Int?, model: ModelReadOnly)

    fun onNewTurn(model: ModelReadOnly)
}