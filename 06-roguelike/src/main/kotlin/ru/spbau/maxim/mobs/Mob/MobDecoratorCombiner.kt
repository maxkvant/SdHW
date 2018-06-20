package ru.spbau.maxim.mobs.Mob

import ru.spbau.maxim.mobs.actions.Action
import ru.spbau.maxim.mobs.effects.StorageEffect
import ru.spbau.maxim.model.Model
import ru.spbau.maxim.model.ModelReadOnly
import kotlin.reflect.KClass

/**
 * Implementation of MobWithEffects, MobDecoratorImpl
 */
open class MobDecoratorCombiner(private val mob: Mob): MobDecoratorImpl(), MobWithEffects {
    private val decorators: MutableList<Node> = mutableListOf()
    private var curMob = mob
    private var curMobReady = true

    init {
        addDecorator(StorageEffect())
    }

    final override fun turn(env: Model): Action {
        onNewTurn(env)
        return getInnerMob().turn(env)
    }

    final override fun getInnerMob(): Mob {
        return curMob
    }

    final override fun addDecorator(decorator: MobDecoratorMutable, timeActive: Int, model: ModelReadOnly) {
        decorators.add(Node(decorator, timeActive + model.turns()))
        curMobReady = false
    }

    final override fun addDecorator(decorator: MobDecoratorMutable) {
        decorators.add(Node(decorator, null))
        curMobReady = false
    }

    final override fun hasDecorator(decoratorClass: KClass<out MobDecoratorMutable>): Boolean {
        return decorators.any { (decorator, _) -> decoratorClass.java.isInstance(decorator) }
    }

    private fun onNewTurn(model: ModelReadOnly) {
        val sizeBefore = decorators.size
        decorators.removeIf { it.timeToRemove != null && it.timeToRemove < model.turns() }
        if (sizeBefore != decorators.size) {
            curMobReady = false
        }

        if (!curMobReady) {
            var newMob = mob
            decorators.forEach { (decorator, _) ->
                decorator.setMob(newMob)
                newMob = decorator
            }
            curMob = newMob
        }
        curMobReady = true
    }

    private data class Node(val mobDecoratorAbstract: MobDecoratorMutable, val timeToRemove: Int?)
}