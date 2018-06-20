package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.model.ModelReadOnly
import kotlin.reflect.KClass

/**
 * You can add effects(decorators) to that kind of Mob
 */
interface MobWithEffects: Mob {
    /**
     * Adds affect of decorator to current MobWithEffects,
     * Effects removes after timeActive
     */
    fun addDecorator(decorator: MobDecoratorMutable, timeActive: Int, model: ModelReadOnly)

    /**
     * Adds affect of decorator to current MobWithEffects,
     * Effects removes after timeActive
     */
    fun addDecorator(decorator: MobDecoratorMutable)

    /**
     * Checks has current mob at least one decorator, that is instance of decoratorClass, or hasn't
     */
    fun hasDecorator(decoratorClass: KClass<out MobDecoratorMutable>): Boolean
}