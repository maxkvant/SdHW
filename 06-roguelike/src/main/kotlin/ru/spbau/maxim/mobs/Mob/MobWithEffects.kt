package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.model.ModelReadOnly
import kotlin.reflect.KClass

interface MobWithEffects: Mob {
    fun addDecorator(decorator: MobDecoratorMutable, timeToLive: Int, model: ModelReadOnly)

    fun addDecorator(decorator: MobDecoratorMutable)

    fun onNewTurn(model: ModelReadOnly)

    fun hasSuch(decoratorClass: KClass<out MobDecoratorMutable>): Boolean
}